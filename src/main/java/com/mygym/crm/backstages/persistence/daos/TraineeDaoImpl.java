package com.mygym.crm.backstages.persistence.daos;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


@Repository("traineeDaoImpl")
public class TraineeDaoImpl implements TraineeDao {

    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Creating trainee with userName: {}", trainee.getUserName());
            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(trainee);

            if (generatedID != null) {
                logger.info("Successfully created trainee with userName: {}", trainee.getUserName());
                return Optional.of(trainee);
            }
        } catch (HibernateError e) {
            logger.error(e.getMessage());
            throw new HibernateException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Updating trainee: {} and userName: {}", trainee.getUserId(), trainee.getUserName());

            Session session = this.sessionFactory.getCurrentSession();
            Trainee newTrainee = (Trainee) session.merge(trainee);
            session.flush();

            return Optional.of(newTrainee);
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public Optional<Trainee> updateByUserName(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Updating trainee with userName: {}", trainee.getUserName());

            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                    UPDATE Trainee
                    SET
                        firstName=:firstName,
                        lastName=:lastName,
                        isActive=:isActive,
                        dateOfBirth=:dateOfBirth,
                        address=:address,
                    WHERE userName =:userName
                    """;
            Trainee updatedTrainee = (Trainee) session.createQuery(sql.strip())
                    .setParameter("userName", trainee.getUserName())
                    .setParameter("firstName", trainee.getFirstName())
                    .setParameter("lastName", trainee.getLastName())
                    .setParameter("isActive", trainee.getIsActive())
                    .setParameter("dateOfBirth", trainee.getDateOfBirth())
                    .setParameter("address", trainee.getAddress())
                    .uniqueResult();

            logger.info("Successfully update trainee with userName: {}", trainee.getUserName());

            return Optional.of(updatedTrainee);

        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Trainee> delete(Long traineeId) {
        checkTrainee(traineeId, Long.class);

        logger.info("Attempting to delete trainee with ID: {}", traineeId);

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Trainee trainee = session.get(Trainee.class, traineeId);
            if (trainee != null) {
                session.delete(trainee);
                logger.info("Deleted trainee with ID: {}", traineeId);

                return Optional.of(trainee);

            } else {
                logger.warn("No trainee found with ID: {} to delete", traineeId);
                return Optional.empty();
            }
        } catch (HibernateException e) {
            logger.error("Failed to delete trainee with ID: {}. Error: {}", traineeId, e.getMessage(), e);
            throw e;
        }

    }

    @Override
    public Optional<Trainee> deleteWithUserName(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to delete trainee with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Trainee trainee = selectWithUserName(userName).orElse(null);
            if (trainee != null) {
                session.delete(trainee);
                logger.info("Deleted trainee with userName: {}", userName);

                return Optional.of(trainee);

            } else {
                logger.warn("No trainee found with userName: {} to delete", userName);
                return Optional.empty();
            }
        } catch (HibernateException e) {
            logger.error("Failed to delete trainee with userName: {}. Error: {}", userName, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Trainee> select(Long traineeId) {
        checkTrainee(traineeId, Long.class);

        logger.info("Attempting to select trainee with ID: {}", traineeId);

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Trainee trainee = session.get(Trainee.class, traineeId);

            if (trainee != null) {
                logger.info("Successfully selected trainee with ID: {}", traineeId);
                return Optional.of(trainee);
            }

            logger.warn("No trainee found with ID: {}", traineeId);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error selecting trainee with ID: {} with message \n" + " {}", traineeId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Trainee> selectWithUserName(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to select trainee with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                      SELECT DISTINCT t FROM Trainee t
                      LEFT JOIN FETCH t.trainings tr
                      LEFT JOIN FETCH tr.trainer trainer
                      LEFT JOIN FETCH trainer.trainingType
                      WHERE t.userName = :userName
                    """;

            Trainee trainee = (Trainee) session.createQuery(sql.strip(), Trainee.class)
                    .setParameter("userName", userName)
                    .uniqueResult();

            if (trainee != null) {
                logger.info("Successfully selected trainee with userName: {}", userName);
                return Optional.of(trainee);
            }

            logger.warn("No trainee found with userName: {}", userName);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error selecting trainee with userName: {}", userName, e);
            throw e;
        }
    }

    @Override
    public boolean changePassword(String userName, String newPassword) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to change password for trainee with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                     UPDATE Trainee t\s
                     SET t.password = :newPassword\s
                     WHERE t.userName = :userName
                    """;

            int affectedRows = session.createQuery(sql.strip())
                    .setParameter("newPassword", newPassword)
                    .setParameter("userName", userName)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully changed password for trainee with userName: {}", userName);
                return true;
            }

            logger.warn("No trainee found to change password with userName: {}", userName);
            return false;
        } catch (Exception e) {
            logger.error("Error changing password for trainee with userName: {}", userName, e);
            throw e;
        }
    }

    @Override
    public boolean toggleIsActive(String userName) {
        checkTrainee(userName, String.class);

        logger.info("Attempting to toggle isActive for trainee with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                    SELECT t.isActive\s
                    From Trainee t\s
                    WHERE t.userName = :userName
                    """;

            Boolean isActive = (Boolean) session.createQuery(sql.strip(), Boolean.class)
                    .setParameter("userName", userName)
                    .uniqueResult();

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

            int affectedRows = session.createQuery(sql.strip())
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

        } catch (Exception e) {
            logger.error("Error toggling isActive for trainee with userName: {}", userName, e);
            throw e;
        }
    }

    public Set<Training> getTraineeTrainings(String userName, LocalDate fromDate, LocalDate toDate,
                                             String trainerName, String trainingTypeName) {
        Set<Training> result = new HashSet<>();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
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

            TypedQuery<Training> query = session.createQuery(cq);

            result = new HashSet<>(query.getResultList());
            logger.info("Retrieved {} training records for user {}.", result.size(), userName);
        } catch (Exception e) {
            logger.error("Error retrieving training records for user {}: {}", userName, e.getMessage(), e);
            throw e;
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
