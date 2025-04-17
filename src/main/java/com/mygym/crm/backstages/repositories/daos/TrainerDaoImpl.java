package com.mygym.crm.backstages.repositories.daos;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.NoResourceException;
import com.mygym.crm.backstages.exceptions.custom.NoTrainerException;
import com.mygym.crm.backstages.exceptions.custom.ResourceCreationException;
import com.mygym.crm.backstages.exceptions.custom.ResourceUpdateException;
import com.mygym.crm.backstages.interfaces.daorepositories.TrainerDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;


@Repository
public class TrainerDaoImpl implements TrainerDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        checkTrainer(trainer, Trainer.class);

        try {
            logger.info("Creating trainer with userName: {}", trainer.getUserName());
            entityManager.persist(trainer);

            logger.info("Successfully created trainer with userName: {}", trainer.getUserName());

            return Optional.of(trainer);

        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }

    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        checkTrainer(trainer, Trainer.class);

        try {
            logger.info("Updating trainer: {}", trainer.getUserId());

            Trainer newTrainer = entityManager.merge(trainer); // JPA merge
            entityManager.flush(); // Optional flush

            return Optional.ofNullable(newTrainer);
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    @Override
    public Optional<Trainer> select(Long trainerId) {
        checkTrainer(trainerId, Long.class);

        logger.info("Attempting to select trainer with ID: {}", trainerId);

        try {
            Trainer trainer = entityManager.find(Trainer.class, trainerId);

            if (trainer != null) {
                logger.info("Successfully selected trainer with ID: {}", trainerId);
                return Optional.of(trainer);
            }

            logger.warn("No trainer found with ID: {}", trainerId);
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Error selecting trainer with ID: {} with message \n {}", trainerId, e.getMessage());
            throw new NoTrainerException("No trainer found with id" + trainerId);
        }
    }

    @Override
    public Optional<Trainer> selectWithUserName(String userName) {
        checkTrainer(userName, String.class);

        logger.info("Attempting to select trainer with userName: {}", userName);

        try {

            String sql = """
                      SELECT t FROM Trainer t\s
                      LEFT JOIN FETCH t.trainings tr\s
                      WHERE t.userName = :userName
                    """;

            Trainer trainer = entityManager.createQuery(sql.strip(), Trainer.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

            if (trainer != null) {
                logger.info("Successfully selected trainer with userName: {}", userName);
                return Optional.of(trainer);
            }

            logger.warn("No trainer found with userName: {}", userName);
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Error selecting trainer with userName: {} with message \n" + " {}", userName, e.getMessage());
            throw new NoTrainerException("No trainer found with userName" + userName);
        }
    }

    @Override
    public boolean changePassword(String userName, String newPassword) {
        checkTrainer(userName, String.class);

        logger.info("Attempting to change password for trainer with userName: {}", userName);

        try {

            String sql = """
                     UPDATE Trainer t\s
                     SET t.password = :newPassword\s
                     WHERE t.userName = :userName
                    """;

            int affectedRows = entityManager.createQuery(sql.strip())
                    .setParameter("newPassword", newPassword)
                    .setParameter("userName", userName)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully changed password for trainer with userName: {}", userName);
                return true;
            }

            logger.warn("No trainer found to change password with userName: {}", userName);
            return false;
        } catch (PersistenceException e) {
            logger.error("Error changing password for trainer with userName: {}", userName, e);
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean toggleIsActive(String userName) {
        checkTrainer(userName, String.class);

        logger.info("Attempting to toggle isActive for trainer with userName: {}", userName);

        try {

            String sql = """
                    SELECT t.isActive\s
                    From Trainer t\s
                    WHERE t.userName = :userName
                    """;

            Boolean isActive = entityManager.createQuery(sql.strip(), Boolean.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

            if (isActive == null) {
                logger.warn("No trainer found to toggle isActive with userName: {}", userName);
                return false;
            }

            boolean newIsActive = !isActive;

            sql = """
                    UPDATE Trainer t\s
                    SET t.isActive = :isActive\s
                    WHERE t.userName = :userName
                    """;

            int affectedRows = entityManager.createQuery(sql.strip())
                    .setParameter("isActive", newIsActive)
                    .setParameter("userName", userName)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully toggled isActive for trainer with userName: {} from: {} to: {}",
                        userName,
                        isActive,
                        newIsActive);
                return true;
            }

            logger.warn("Failed to toggle isActive for trainer with userName: {}", userName);
            return false;

        } catch (PersistenceException e) {
            logger.error("Error toggling isActive for trainer with userName: {}", userName, e);
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    public Set<Training> getTrainerTrainings(String userName, LocalDate fromDate, LocalDate toDate,
                                             String traineeName) {
        Set<Training> result;
        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Training> cq = cb.createQuery(Training.class);
            Root<Training> trainingRoot = cq.from(Training.class);

            Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

            List<Predicate> predicates = new ArrayList<>();

            if (userName != null && !userName.trim().isEmpty()) {
                predicates.add(cb.equal(trainerJoin.get("userName"), userName));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(trainingRoot.get("trainingDate"), toDate));
            }

            if (traineeName != null && !traineeName.trim().isEmpty()) {
                predicates.add(cb.equal(traineeJoin.get("firstName"), traineeName));
            }

            cq.select(trainingRoot).where(predicates.toArray(new Predicate[0]));

            TypedQuery<Training> query = entityManager.createQuery(cq);

            result = new HashSet<>(query.getResultList());
            logger.info("Retrieved {} training records for user {}.", result.size(), userName);
        } catch (PersistenceException e) {
            logger.error("Error retrieving training records for user {}: {}", userName, e.getMessage(), e);
            throw new NoResourceException("No trianings");
        }
        return result;
    }

    private <T> void checkTrainer(T trainer, Class<T> tClass) {
        if (trainer == null) {
            String className = tClass.getName();
            switch (className) {
                case "com/mygym/crm/backstages/domain/models/Trainer.java":
                    logger.error("Trainer is null");
                    throw new IllegalArgumentException("Trainer must not be null");
                case "java.lang.Long":
                    logger.error("Trainer ID is null");
                    throw new IllegalArgumentException("Trainer ID must not be null");
                case "java.lang.String":
                    logger.error("Trainer userName is null");
                    throw new IllegalArgumentException("Trainer userName must not be null");
                default:
                    throw new IllegalArgumentException("Unexpected type: " + className);
            }
        }
    }
}
