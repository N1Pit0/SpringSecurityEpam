package com.mygym.crm.backstages.persistence.daos.traineedao;

import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.persistence.storages.TraineeStorage;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("traineeDAOIMPL")
public class TraineeDaoImpl implements TraineeDao {

    @Getter
    private TraineeStorage traineeStorage;
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Autowired
    public void setTraineeStorage(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        Trainee existingTrainee = traineeStorage.getStorage()
                .putIfAbsent(trainee.getUserId(), trainee);

        if (existingTrainee == null) {
            logger.info("Trainee created successfully with ID: {}", trainee.getUserId());

            UserService.uniqueID++;

            return Optional.of(trainee);
        }

        logger.warn("Trainee already exists with ID: {}", existingTrainee.getUserId());
        return Optional.empty();
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        Trainee previous = traineeStorage.getStorage().replace(trainee.getUserId(), trainee);

        if (previous != null) {
            logger.info("Trainee updated successfully: {}", trainee.getUserId());
            return Optional.of(trainee);
        }

        logger.warn("Trainee with ID {} does not exist, cant update", trainee.getUserId());
        return Optional.empty();
    }

    @Override
    public Optional<Trainee> delete(Integer traineeId) {
        Trainee removedTrainee  = traineeStorage.getStorage().remove(traineeId);

        if (removedTrainee != null) {
            logger.info("Trainee deleted successfully: {}", traineeId);
            return Optional.of(removedTrainee);
        }

        logger.warn("Trainee with ID {} does not exist, cant delete", traineeId);
        return Optional.empty();
    }

    @Override
    public Optional<Trainee> select(Integer traineeId) {
        Trainee trainee = traineeStorage.getStorage().get(traineeId);

        if (trainee != null) {
            logger.info("Trainee found successfully: {}", traineeId);
            return Optional.of(trainee);
        }

        logger.warn("Trainee with ID {} does not exist, cant be found", traineeId);
        return Optional.empty();
    }
}
