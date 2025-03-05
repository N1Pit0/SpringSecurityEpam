package com.mygym.crm.backstages.persistence.daos.trainingdao;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    @Override
    public Optional<Training> select(Long trainingKey) {

        return Optional.empty();
    }

    @Override
    public Optional<Training> create(Training training) {
        return Optional.empty();
    }


}
