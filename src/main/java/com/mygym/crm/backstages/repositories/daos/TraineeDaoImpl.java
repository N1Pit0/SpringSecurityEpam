package com.mygym.crm.backstages.repositories.daos;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.exceptions.custom.*;
import com.mygym.crm.backstages.interfaces.daorepositories.TraineeDao;
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


@Repository("traineeDaoImpl")
public class TraineeDaoImpl implements TraineeDao {

    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Creating trainee with userName: {}", trainee.getUserName());
            entityManager.persist(trainee);

            logger.info("Successfully created trainee with userName: {}", trainee.getUserName());
            return Optional.of(trainee);

        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceCreationException(e.getMessage());
        }

    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Updating trainee: {} and userName: {}", trainee.getUserId(), trainee.getUserName());

            Trainee newTrainee = entityManager.merge(trainee); // JPA merge
            entityManager.flush(); // Optional flush

            return Optional.of(newTrainee);
        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    @Override
    public Optional<Trainee> updateByUserName(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Updating trainee with userName: {}", trainee.getUserName());

            String sql = """
                        UPDATE Trainee t
                        SET t.firstName = :firstName,
                            t.lastName = :lastName,
                            t.isActive = :isActive,
                            t.dateOfBirth = :dateOfBirth,
                            t.address = :address
                        WHERE t.userName = :userName
                    """;

            Trainee updatedTrainee = (Trainee) entityManager.createQuery(sql.strip())
                    .setParameter("userName", trainee.getUserName())
                    .setParameter("firstName", trainee.getFirstName())
                    .setParameter("lastName", trainee.getLastName())
                    .setParameter("isActive", trainee.getIsActive())
                    .setParameter("dateOfBirth", trainee.getDateOfBirth())
                    .setParameter("address", trainee.getAddress())
                    .getSingleResult();

            logger.info("Successfully update trainee with userName: {}", trainee.getUserName());

            return Optional.of(updatedTrainee);

        } catch (PersistenceException e) {
            logger.error(e.getMessage());
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    @Override
    public Optional<Trainee> delete(Long traineeId) {
        checkTrainee(traineeId, Long.class);

        logger.info("Attempting to delete trainee with ID: {}", traineeId);

        try {

            Trainee trainee = entityManager.find(Trainee.class, traineeId);

            if (trainee != null) {
                entityManager.remove(trainee);
                logger.info("Deleted trainee with ID: {}", traineeId);

                return Optional.of(trainee);

            } else {
                logger.warn("No trainee found with ID: {} to delete", traineeId);
                return Optional.empty();
            }
        } catch (PersistenceException e) {
            logger.error("Failed to delete trainee with ID: {}. Error: {}", traineeId, e.getMessage(), e);
            throw new ResourceDeletionException(e.getMessage());
        }

    }

    @Override
    public Optional<Trainee> deleteWithUserName(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to delete trainee with userName: {}", userName);

        try {
            Trainee trainee = selectWithUserName(userName).orElse(null);
            if (trainee != null) {
                entityManager.remove(trainee);
                logger.info("Deleted trainee with userName: {}", userName);

                return Optional.of(trainee);

            } else {
                logger.warn("No trainee found with userName: {} to delete", userName);
                return Optional.empty();
            }
        } catch (PersistenceException e) {
            logger.error("Failed to delete trainee with userName: {}. Error: {}", userName, e.getMessage(), e);
            throw new ResourceDeletionException(e.getMessage());
        }
    }

    @Override
    public Optional<Trainee> select(Long traineeId) {
        checkTrainee(traineeId, Long.class);

        logger.info("Attempting to select trainee with ID: {}", traineeId);

        try {
            Trainee trainee = entityManager.find(Trainee.class, traineeId);

            if (trainee != null) {
                logger.info("Successfully selected trainee with ID: {}", traineeId);
                return Optional.of(trainee);
            }

            logger.warn("No trainee found with ID: {}", traineeId);
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Error selecting trainee with ID: {} with message \n" + " {}", traineeId, e.getMessage());
            throw new NoTraineeException("No trainee found with id" + traineeId);
        }
    }

    @Override
    public Optional<Trainee> selectWithUserName(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to select trainee with userName: {}", userName);

        try {

            String sql = """
                      SELECT DISTINCT t FROM Trainee t
                      LEFT JOIN FETCH t.trainings tr
                      LEFT JOIN FETCH tr.trainer trainer
                      LEFT JOIN FETCH trainer.trainingType
                      WHERE t.userName = :userName
                    """;

            Trainee trainee = entityManager.createQuery(sql.strip(), Trainee.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

            if (trainee != null) {
                logger.info("Successfully selected trainee with userName: {}", userName);
                return Optional.of(trainee);
            }

            logger.warn("No trainee found with userName: {}", userName);
            return Optional.empty();
        } catch (PersistenceException e) {
            logger.error("Error selecting trainee with userName: {}", userName, e);
            throw new NoTraineeException(e.getMessage());
        }
    }

    @Override
    public boolean changePassword(String userName, String newPassword) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to change password for trainee with userName: {}", userName);

        try {

            String sql = """
                     UPDATE Trainee t\s
                     SET t.password = :newPassword\s
                     WHERE t.userName = :userName
                    """;

            int affectedRows = entityManager.createQuery(sql.strip())
                    .setParameter("newPassword", newPassword)
                    .setParameter("userName", userName)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully changed password for trainee with userName: {}", userName);
                return true;
            }

            logger.warn("No trainee found to change password with userName: {}", userName);
            return false;
        } catch (PersistenceException e) {
            logger.error("Error changing password for trainee with userName: {}", userName, e);
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    @Override
    public boolean toggleIsActive(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to toggle isActive for trainee with userName: {}", userName);

        try {

            String sql = """
                    SELECT t.isActive\s
                    From Trainee t\s
                    WHERE t.userName = :userName
                    """;

            Boolean isActive = entityManager.createQuery(sql.strip(), Boolean.class)
                    .setParameter("userName", userName)
                    .getSingleResult();

            if (isActive == null) {
                logger.warn("No trainee found to toggle isActive with userName: {}", userName);
                return false;
            }

            boolean newIsActive = !isActive;

            sql = """
                    UPDATE Trainee t\s
                    SET t.isActive = :isActive\s
                    WHERE t.userName = :userName
                    """;

            int affectedRows = entityManager.createQuery(sql.strip())
                    .setParameter("isActive", newIsActive)
                    .setParameter("userName", userName)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully toggled isActive for trainee with userName: {} from: {} to: {}",
                        userName,
                        isActive,
                        newIsActive);
                return true;
            }

            logger.warn("Failed to toggle isActive for trainee with userName: {}", userName);
            return false;

        } catch (PersistenceException e) {
            logger.error("Error toggling isActive for trainee with userName: {}", userName, e);
            throw new ResourceUpdateException(e.getMessage());
        }
    }

    public Set<Training> getTraineeTrainings(String userName, LocalDate fromDate, LocalDate toDate,
                                             String trainerName, String trainingTypeName) {
        Set<Training> result;

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Training> cq = cb.createQuery(Training.class);
            Root<Training> trainingRoot = cq.from(Training.class);

            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

            Join<Training, TrainingType> trainingTypeJoin = trainingRoot.join("trainingType");

            Join<Training, Trainer> trainerJoin = trainingRoot.join("trainer");

            List<Predicate> predicates = new ArrayList<>();

            if (userName != null && !userName.trim().isEmpty()) {
                predicates.add(cb.equal(traineeJoin.get("userName"), userName));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(trainingRoot.get("trainingDate"), toDate));
            }

            if (trainerName != null && !trainerName.trim().isEmpty()) {
                predicates.add(cb.equal(trainerJoin.get("firstName"), trainerName));
            }

            if (trainingTypeName != null && !trainingTypeName.trim().isEmpty()) {
                predicates.add(cb.equal(trainingTypeJoin.get("trainingTypeName"), trainingTypeName));
            }

            cq.select(trainingRoot).where(predicates.toArray(new Predicate[0]));

            TypedQuery<Training> query = entityManager.createQuery(cq);

            result = new HashSet<>(query.getResultList());
            logger.info("Retrieved {} training records for user {}.", result.size(), userName);
        } catch (PersistenceException e) {
            logger.error("Error retrieving training records for user {}: {}", userName, e.getMessage(), e);
            throw new NoResourceException(e.getMessage());
        }
        return result;
    }

    @Override
    public Set<Trainer> getTrainersNotTrainingTraineesWithUserName(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Trying to get Trainers that do not teach trainees with userName: {}", userName);

        Set<Trainer> result;

        try {

            String sql = """
                         SELECT tr\s
                         FROM Trainer tr\s
                         WHERE NOT EXISTS (
                             SELECT 1\s
                             FROM Trainee t\s
                             JOIN t.trainings trn\s
                             WHERE trn.trainer = tr AND t.userName = :userName
                         ) AND tr.isActive = true
                    """;
            result = new HashSet<>(entityManager.createQuery(sql.strip(), Trainer.class)
                    .setParameter("userName", userName)
                    .getResultList());
        } catch (PersistenceException e) {
            logger.error("Error retrieving Trainers that do not teach trainees with userName: {} : {}", userName, e.getMessage(), e);
            throw new NoResourceException(e.getMessage());
        }
        return result;
    }

    private <T> void checkTrainee(T trainee, Class<T> tClass) {
        if (trainee == null) {
            String className = tClass.getName();
            switch (className) {
                case "com/mygym/crm/backstages/domain/models/Trainee":
                    logger.error("Trainee is null");
                    throw new IllegalArgumentException("Trainee must not be null");
                case "java.lang.Long":
                    logger.error("Trainee ID is null");
                    throw new IllegalArgumentException("Trainee ID must not be null");
                case "java.lang.String":
                    logger.error("Trainee userName is null");
                    throw new IllegalArgumentException("Trainee userName must not be null");
                default:
                    throw new IllegalArgumentException("Unexpected type: " + className);
            }
        }
    }

}
