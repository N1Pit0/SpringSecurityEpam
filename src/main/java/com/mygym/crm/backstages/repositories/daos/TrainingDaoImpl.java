package com.mygym.crm.backstages.repositories.daos;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.NoTrainingException;
import com.mygym.crm.backstages.exceptions.custom.ResourceCreationException;
import com.mygym.crm.backstages.exceptions.custom.ResourceDeletionException;
import com.mygym.crm.backstages.interfaces.daorepositories.TrainingDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Getter
@Repository
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Training> select(Long trainingKey) {

        logger.info("Attempting to select Training with ID: {}", trainingKey);

        try {
            Training training = entityManager.find(Training.class, trainingKey);

            if (training != null) {
                logger.info("Successfully selected Training with ID: {}", trainingKey);
                return Optional.of(training);
            }

            logger.warn("No Training found with ID: {}", trainingKey);
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Error selecting Training with ID: {} with error \n{}", trainingKey, e.getMessage());
            throw new NoTrainingException(e.getMessage());
        }
    }

    @Override
    public Optional<Training> add(Training training) {
        try {
            logger.info("Creating training with id: {}", training.getId());

            entityManager.persist(training);

            logger.info("Successfully created trainer with id: {}", training.getId());

            return Optional.of(training);
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }

    }

    public int deleteWithTraineeUsername(String traineeUsername) {
        try {
            logger.info("Attempting to delete Training with traineeUsername: {}", traineeUsername);

            String sql = """
                    DELETE FROM Training t
                    WHERE t.trainee.userName = :traineeUsername
                    """;

            int deletedCount = entityManager.createQuery(sql.strip())
                    .setParameter("traineeUsername", traineeUsername)
                    .executeUpdate();

            if (deletedCount > 0) {
                logger.info("Successfully deleted {} rows", deletedCount);
            } else {
                logger.error("Failed to delete Training with traineeUsername: {}", traineeUsername);
            }

            return deletedCount;
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceDeletionException(e.getMessage());
        }
    }
}
