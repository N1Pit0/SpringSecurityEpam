package com.mygym.crm.backstages.persistence.daos;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
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


@Repository
public class TrainerDaoImpl implements TrainerDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        checkTrainer(trainer, Trainer.class);

        try {
            logger.info("Creating trainer with userName: {}", trainer.getUserName());
            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(trainer);

            if (generatedID != null) {
                logger.info("Successfully created trainer with userName: {}", trainer.getUserName());
                return Optional.of(trainer);
            }
        } catch (HibernateError e) {
            logger.error(e.getMessage());
            throw e;
        }

        return Optional.empty();
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        checkTrainer(trainer, Trainer.class);

        try {
            logger.info("Updating trainer: {}", trainer.getUserId());

            Session session = this.sessionFactory.getCurrentSession();
            Trainer newTrainer = (Trainer) session.merge(trainer);
            session.flush();

            return Optional.ofNullable(newTrainer);
        } catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Trainer> select(Long trainerId) {
        checkTrainer(trainerId, Long.class);

        logger.info("Attempting to select trainer with ID: {}", trainerId);

        try {
            Session session = this.sessionFactory.getCurrentSession();
            Trainer trainer = session.get(Trainer.class, trainerId);

            if (trainer != null) {
                logger.info("Successfully selected trainer with ID: {}", trainerId);
                return Optional.of(trainer);
            }

            logger.warn("No trainer found with ID: {}", trainerId);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error selecting trainer with ID: {} with message \n {}", trainerId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Optional<Trainer> selectWithUserName(String userName) {
        checkTrainer(userName, String.class);

        logger.info("Attempting to select trainer with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                      SELECT t FROM Trainer t\s
                      LEFT JOIN FETCH t.trainings tr\s
                      WHERE t.userName = :userName
                    """;

            Trainer trainer = (Trainer) session.createQuery(sql.strip(), Trainer.class)
                    .setParameter("userName", userName)
                    .uniqueResult();

            if (trainer != null) {
                logger.info("Successfully selected trainer with userName: {}", userName);
                return Optional.of(trainer);
            }

            logger.warn("No trainer found with userName: {}", userName);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error selecting trainer with userName: {} with message \n" + " {}", userName, e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean changePassword(String userName, String newPassword) {
        checkTrainer(userName, String.class);

        logger.info("Attempting to change password for trainer with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                     UPDATE Trainer t\s
                     SET t.password = :newPassword\s
                     WHERE t.userName = :userName
                    """;

            int affectedRows = session.createQuery(sql.strip())
                    .setParameter("newPassword", newPassword)
                    .setParameter("userName", userName)
                    .executeUpdate();

            if (affectedRows == 1) {
                logger.info("Successfully changed password for trainer with userName: {}", userName);
                return true;
            }

            logger.warn("No trainer found to change password with userName: {}", userName);
            return false;
        } catch (Exception e) {
            logger.error("Error changing password for trainer with userName: {}", userName, e);
            throw e;
        }
    }

    @Override
    public boolean toggleIsActive(String userName) {
        checkTrainer(userName, String.class);

        logger.info("Attempting to toggle isActive for trainer with userName: {}", userName);

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                    SELECT t.isActive\s
                    From Trainer t\s
                    WHERE t.userName = :userName
                    """;

            Boolean isActive = (Boolean) session.createQuery(sql.strip(), Boolean.class)
                    .setParameter("userName", userName)
                    .uniqueResult();

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

            int affectedRows = session.createQuery(sql.strip())
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

        } catch (Exception e) {
            logger.error("Error toggling isActive for trainer with userName: {}", userName, e);
            throw e;
        }
    }

    public Set<Training> getTrainerTrainings(String userName, LocalDate fromDate, LocalDate toDate,
                                             String traineeName) {
        Set<Training> result = new HashSet<>();
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
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

            TypedQuery<Training> query = session.createQuery(cq);

            result = new HashSet<>(query.getResultList());
            logger.info("Retrieved {} training records for user {}.", result.size(), userName);
        } catch (Exception e) {
            logger.error("Error retrieving training records for user {}: {}", userName, e.getMessage(), e);
            throw e;
        }
        return result;
    }

    @Override
    public List<Trainer> getTrainersNotTrainingTraineesWithUserName(String userName) {
        checkTrainer(userName, String.class);

        logger.info("Trying to get Trainers that do not teach trainees with userName: {}", userName);

        List<Trainer> result = new ArrayList<>();

        try {
            Session session = this.sessionFactory.getCurrentSession();

            String sql = """
                         SELECT tr\s
                         FROM Trainer tr\s
                         WHERE NOT EXISTS (
                             SELECT 1\s
                             FROM Trainee t\s
                             JOIN t.trainings trn\s
                             WHERE trn.trainer = tr AND t.userName = :userName
                         )
                    """;
            result = session.createQuery(sql.strip(), Trainer.class)
                    .setParameter("userName", userName)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error retrieving Trainers that do not teach trainees with userName: {} : {}", userName, e.getMessage(), e);
            throw e;
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
