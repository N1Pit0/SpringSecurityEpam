package com.mygym.crm.backstages.persistence.daos.trainerdao;

import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;


@Repository
public class TrainerDaoImpl implements TrainerDao {

    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Optional<Trainer> create(Trainer trainer) {
        if (trainer == null) {
            logger.error("trainer is null");
            throw new IllegalArgumentException("Trainer must not be null");
        }

        try{
            logger.info("Creating trainee: {}", trainer.getUserId());

            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(trainer);

            if (generatedID != null) return Optional.of(trainer);
        }
        catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }

        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Trainer> update(Trainer trainer) {

        if (trainer == null) {
            logger.error("trainee is null");
            throw new IllegalArgumentException("Trainee must not be null");
        }

        try{
            logger.info("Updating trainee: {}", trainer.getUserId());

            Session session = this.sessionFactory.getCurrentSession();
            Trainer newTrainer = (Trainer) session.merge(trainer);
            session.flush();

            return Optional.ofNullable(newTrainer);
        }catch (HibernateException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    @Override
    public Optional<Trainer> select(Long trainerId) {

        return Optional.empty();
    }
}
