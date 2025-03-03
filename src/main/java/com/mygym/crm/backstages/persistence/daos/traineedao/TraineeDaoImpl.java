package com.mygym.crm.backstages.persistence.daos.traineedao;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import lombok.Getter;
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

    @Getter
    private SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Optional<Trainee> create(Trainee trainee) {
        try {
            Session session = this.sessionFactory.getCurrentSession();
            Serializable generatedID = session.save(trainee);

            if (generatedID != null) return Optional.of(trainee);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        return Optional.empty();
    }

    @Override
    public Optional<Trainee> delete(Long traineeId) {
        return Optional.empty();
    }

    @Override
    public Optional<Trainee> select(Long traineeId) {
        return Optional.empty();
    }

}
