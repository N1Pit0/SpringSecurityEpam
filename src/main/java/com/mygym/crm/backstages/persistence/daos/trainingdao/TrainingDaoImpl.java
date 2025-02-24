package com.mygym.crm.backstages.persistence.daos.trainingdao;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
import com.mygym.crm.backstages.persistence.storages.TrainingStorage;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingDaoImpl implements TrainingDao {

    private TrainingStorage trainingStorage;
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);

    @Autowired
    public void setTrainingStorage(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public Optional<Training> create(Training training) {
        Training existingTraining =  trainingStorage.getStorage()
                .putIfAbsent(training.getTrainingKey(),training);

        if (existingTraining == null) {
            logger.info("Training created successfully with ID: {}", training.getTrainingKey().toString());
            return Optional.of(training);
        }

        logger.warn("Training already exists with ID: {}", existingTraining.getTrainingKey().toString());
        return Optional.empty();
    }

    @Override
    public Optional<Training> update(Training training) {
        Training previous = trainingStorage.getStorage().replace(training.getTrainingKey(), training);

        if (previous != null) {
            logger.info("Training updated successfully: {}", training.getTrainingKey().toString());
            return Optional.of(training);
        }

        logger.warn("Training with ID {} does not exist, cant update", training.getTrainingKey().toString());
        return Optional.empty();
    }

    @Override
    public Optional<Training> delete(TrainingKey trainingKey) {
        Training removedTraining = trainingStorage.getStorage().remove(trainingKey);

        if (removedTraining != null) {
            logger.info("Training deleted successfully: {}", trainingKey.toString());
            return Optional.of(removedTraining);
        }

        logger.warn("Training with ID {} does not exist, cant delete", trainingKey.toString());
        return Optional.empty();
    }

    @Override
    public Optional<Training> select(TrainingKey trainingKey) {
        Training training = trainingStorage.getStorage().get(trainingKey);

        if (training != null) {
            logger.info("Trainer found successfully: {}", trainingKey.toString());
            return Optional.of(training);
        }

        logger.warn("Trainer with ID {} does not exist, cant be found", trainingKey.toString());
        return Optional.empty();
    }
}
