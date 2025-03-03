package com.mygym.crm.backstages.persistence.daos.traineedao;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;


@Repository("traineeDAOIMPL")
public class TraineeDaoImpl implements TraineeDao {

    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Optional<Trainee> create(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try{
            logger.info("Creating trainee: {}", trainee.getUserId());
            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(trainee);

            if (generatedID != null) return Optional.of(trainee);
        }
        catch (HibernateError e) {
            logger.error(e.getMessage());
            throw new HibernateException(e);
        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Trainee> update(Trainee trainee) {
        checkTrainee(trainee, Trainee.class);

        try{
            logger.info("Updating trainee: {}", trainee.getUserId());

            Session session = this.sessionFactory.getCurrentSession();
            Trainee newTrainee = (Trainee) session.merge(trainee);
            session.flush();

            return Optional.ofNullable(newTrainee);
        }catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> delete(Long traineeId) {
        checkTrainee(traineeId, Long.class);

        logger.info("Attempting to delete trainee with ID: {}", traineeId);

        Session session = this.sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, traineeId);
        if (trainee != null) {
            try {
                session.delete(trainee);
                logger.info("Deleted trainee with ID: {}", traineeId);
                return Optional.of(trainee);
            } catch (HibernateException e) {
                logger.error("Failed to delete trainee with ID: {}. Error: {}", traineeId, e.getMessage(), e);
                throw e;
            }
        } else {
            logger.info("No trainee found with ID: {}", traineeId);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> deleteWithUserName(String username) {
        checkTrainee(username, String.class);

        logger.info("Attempting to delete trainee with username: {}", username);

        Session session = this.sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, username);
        if (trainee != null) {
            try {
                session.delete(trainee);
                logger.info("Deleted trainee with username: {}", username);
                return Optional.of(trainee);
            } catch (HibernateException e) {
                logger.error("Failed to delete trainee with username: {}. Error: {}", username, e.getMessage(), e);
                throw e;
            }
        } else {
            logger.info("No trainee found with username: {}", username);
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> select(Long traineeId) {
        checkTrainee(traineeId, Long.class);

        logger.info("Attempting to select trainee with ID: {}", traineeId);

        Session session = this.sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, traineeId);

        if (trainee != null) return Optional.of(trainee);

        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Trainee> selectWithUserName(String username) {
        checkTrainee(username, String.class);

        logger.info("Attempting to select trainee with username: {}", username);

        Session session = this.sessionFactory.getCurrentSession();
        Trainee trainee = session.get(Trainee.class, username);

        if (trainee != null) return Optional.of(trainee);

        return Optional.empty();
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
