package com.mygym.crm.backstages.persistence.daos.traineedao;

import com.mygym.crm.backstages.domain.models.Trainee;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository("traineeDAOIMPL")
public class TraineeDaoImpl implements TraineeDao {

    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try {
            logger.info("Creating trainee with UserName: {}", trainee.getUserName());
            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(trainee);

            if (generatedID != null) {
                logger.info("Successfully created trainee with UserName: {}", trainee.getUserName());
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
            logger.info("Updating trainee: {}", trainee.getUserId());

            Session session = this.sessionFactory.getCurrentSession();
            Trainee newTrainee = (Trainee) session.merge(trainee);
            session.flush();

            return Optional.ofNullable(newTrainee);
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
    public Optional<Trainee> deleteWithUserName(String username) {
        checkTrainee(username, String.class);

        logger.info("Attempting to delete trainee with username: {}", username);

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Trainee trainee = selectWithUserName(username).orElse(null);
            if (trainee != null) {
                session.delete(trainee);
                logger.info("Deleted trainee with username: {}", username);

                return Optional.of(trainee);

            } else {
                logger.warn("No trainee found with username: {} to delete", username);
                return Optional.empty();
            }
        } catch (HibernateException e) {
            logger.error("Failed to delete trainee with username: {}. Error: {}", username, e.getMessage(), e);
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
            logger.error("Error selecting trainee with ID: {}", traineeId, e);
            throw e;
        }
    }

    @Override
    public Optional<Trainee> selectWithUserName(String username) {
        checkTrainee(username, String.class);

        logger.info("Attempting to select trainee with username: {}", username);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
             SELECT t FROM Trainee t\s
             LEFT JOIN FETCH t.trainings tr\s
             WHERE t.userName = :username
           """;

            Trainee trainee = (Trainee) session.createQuery(sql.strip(), Trainee.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (trainee != null) {
                logger.info("Successfully selected trainee with username: {}", username);
                return Optional.of(trainee);
            }

            logger.warn("No trainee found with username: {}", username);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error selecting trainee with username: {}", username, e);
            throw e;
        }
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        checkTrainee(username, String.class);

        logger.info("Attempting to change password for trainee with username: {}", username);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                UPDATE Trainee t\s
                SET t.password = :newPassword\s
                WHERE t.userName = :username
               """;

            int affectedRows = session.createQuery(sql.strip())
                    .setParameter("newPassword", newPassword)
                    .setParameter("username", username)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully changed password for trainee with username: {}", username);
                return true;
            }

            logger.warn("No trainee found to change password with username: {}", username);
            return false;
        } catch (Exception e) {
            logger.error("Error changing password for trainee with username: {}", username, e);
            throw e;
        }
    }

    @Override
    public boolean toggleIsActive(String username) {
        checkTrainee(username, String.class);

        logger.info("Attempting to toggle isActive for trainee with username: {}", username);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                    SELECT t.isActive\s
                    From Trainee t\s
                    WHERE t.userName = :username
                    """;

            Boolean isActive = (Boolean) session.createQuery(sql.strip(), Boolean.class)
                    .setParameter("username", username)
                    .uniqueResult();

            if (isActive == null) {
                logger.warn("No trainee found to toggle isActive with username: {}", username);
                return false;
            }

            boolean newIsActive = !isActive;

            sql = """
                    UPDATE Trainee t\s
                    SET t.isActive = :isActive\s
                    WHERE t.userName = :username
                    """;

            int affectedRows = session.createQuery(sql.strip())
                    .setParameter("isActive", newIsActive)
                    .setParameter("username", username)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully toggled isActive for trainee with username: {} from: {} to: {}",
                        username,
                        isActive,
                        newIsActive);
                return true;
            }

            logger.warn("Failed to toggle isActive for trainee with username: {}", username);
            return false;

        }catch (Exception e) {
            logger.error("Error toggling isActive for trainee with username: {}", username, e);
            throw e;
        }
    }

    public List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate,
                                              String trainerName, String trainingTypeName) {
        List<Training> result = new ArrayList<>();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Training> cq = cb.createQuery(Training.class);
            Root<Training> trainingRoot = cq.from(Training.class);

            Join<Training, Trainee> traineeJoin = trainingRoot.join("trainee");

            Join<Training, TrainingType> trainingTypeJoin = trainingRoot.join("trainingType");

            List<Predicate> predicates = new ArrayList<>();

            if (username != null && !username.trim().isEmpty()) {
                predicates.add(cb.equal(traineeJoin.get("username"), username));
            }

            if (fromDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(trainingRoot.get("trainingDate"), fromDate));
            }

            if (toDate != null) {
                predicates.add(cb.lessThanOrEqualTo(trainingRoot.get("trainingDate"), toDate));
            }

            if (trainerName != null && !trainerName.trim().isEmpty()) {
                predicates.add(cb.equal(trainingRoot.get("trainerName"), trainerName));
            }

            if (trainingTypeName != null && !trainingTypeName.trim().isEmpty()) {
                predicates.add(cb.equal(trainingTypeJoin.get("trainingTypeName"), trainingTypeName));
            }

            cq.select(trainingRoot).where(predicates.toArray(new Predicate[0]));

            TypedQuery<Training> query = session.createQuery(cq);

            result = query.getResultList();
            logger.info("Retrieved {} training records for user {}.", result.size(), username);
        } catch (Exception e) {
            logger.error("Error retrieving training records for user {}: {}", username, e.getMessage(), e);
             throw e;
        }
        return result;
    }

    private <T> void checkTrainee(T trainee, Class<T> tClass) {
        if (trainee == null) {
            String className = tClass.getName();
            switch (className) {
                case "com/mygym/crm/backstages/domain/models/Trainee.java":
                    logger.error("Trainee is null");
                    throw new IllegalArgumentException("Trainee must not be null");
                case "java.lang.Long":
                    logger.error("Trainee ID is null");
                    throw new IllegalArgumentException("Trainee ID must not be null");
                case "java.lang.String":
                    logger.error("Trainee username is null");
                    throw new IllegalArgumentException("Trainee username must not be null");
                default:
                    throw new IllegalArgumentException("Unexpected type: " + className);
            }
        }
    }

}
