package com.mygym.crm.backstages.persistence.daos.trainerdao;

import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class TrainerDaoImpl implements TrainerDao {

    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Override
    public Optional<Trainer> create(Trainer trainer) {

        return Optional.empty();
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {

        return Optional.empty();
    }

    @Override
    public Optional<Trainer> delete(Integer trainerId) {

        return Optional.empty();
    }

    @Override
    public Optional<Trainer> select(Integer trainerId) {

        return Optional.empty();
    }
}
