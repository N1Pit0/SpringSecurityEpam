package com.mygym.crm.backstages.persistence.daos;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.NoTrainingException;
import com.mygym.crm.backstages.exceptions.custom.ResourceCreationException;
import com.mygym.crm.backstages.exceptions.custom.ResourceDeletionException;
import com.mygym.crm.backstages.interfaces.daorepositories.TrainingDao;
import lombok.Getter;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Repository
public class TrainingDaoImpl implements TrainingDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Training> select(Long trainingKey) {

        logger.info("Attempting to select Training with ID: {}", trainingKey);

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Training training = session.get(Training.class, trainingKey);

            if (training != null) {
                logger.info("Successfully selected Training with ID: {}", trainingKey);
                return Optional.of(training);
            }

            logger.warn("No Training found with ID: {}", trainingKey);
            return Optional.empty();
        } catch (HibernateException e) {
            logger.error("Error selecting Training with ID: {} with error \n{}", trainingKey, e.getMessage());
            throw new NoTrainingException(e.getMessage());
        }
    }

    @Override
    public Optional<Training> add(Training training) {
        try {
            logger.info("Creating training with id: {}", training.getId());
            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(training);

            if (generatedID != null) {
                logger.info("Successfully created trainer with id: {}", training.getId());

                return Optional.of(training);
            }
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }

        return Optional.empty();
    }

    public int deleteWithTraineeUsername(String traineeUsername) {
        try {
            logger.info("Attempting to delete Training with traineeUsername: {}", traineeUsername);

            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                    DELETE FROM Training t
                    WHERE t.trainee.userName = :traineeUsername
                    """;

            int deletedCount = session.createQuery(sql.strip())
                    .setParameter("traineeUsername", traineeUsername)
                    .executeUpdate();

            if(deletedCount > 0){
                logger.info("Successfully deleted {} rows", deletedCount);
            }else {
                logger.error("Failed to delete Training with traineeUsername: {}", traineeUsername);
            }

            return deletedCount;
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new ResourceDeletionException(e.getMessage());
        }
    }
}
