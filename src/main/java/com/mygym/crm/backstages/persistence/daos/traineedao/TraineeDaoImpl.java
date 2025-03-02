package com.mygym.crm.backstages.persistence.daos.traineedao;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("traineeDAOIMPL")
public class TraineeDaoImpl implements TraineeDao {

    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Override
    public Optional<Trainee> create(Trainee trainee) {
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
