package com.mygym.crm.backstages.persistence.daos.trainerdao;

import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.persistence.storages.TrainerStorage;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class TrainerDaoImpl implements TrainerDao {

    private TrainerStorage trainerStorage;
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Autowired
    public void setTrainerStorage(TrainerStorage trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public Optional<Trainer> create(Trainer trainer) {
        Trainer existingTrainer = trainerStorage.getStorage()
                .putIfAbsent(trainer.getUserId(), trainer);

        if (existingTrainer == null) {
            logger.info("Trainer created successfully with ID: {}", trainer.getUserId());

            UserService.uniqueID++;

            return Optional.of(trainer);
        }

        logger.warn("Trainer already exists with ID: {}", existingTrainer.getUserId());
        return Optional.empty();
    }

    @Override
    public Optional<Trainer> update(Trainer trainer) {
        Trainer previous = trainerStorage.getStorage().replace(trainer.getUserId(), trainer);

        if (previous != null) {
            logger.info("Trainer updated successfully: {}", trainer.getUserId());
            return Optional.of(trainer);
        }

        logger.warn("Trainer with ID {} does not exist, cant update", trainer.getUserId());
        return Optional.empty();
    }

    @Override
    public Optional<Trainer> delete(Integer trainerId) {
        Trainer removedTrainer = trainerStorage.getStorage().remove(trainerId);

        if (removedTrainer != null) {
            logger.info("Trainer deleted successfully: {}", trainerId);
            return Optional.of(removedTrainer);
        }

        logger.warn("Trainer with ID {} does not exist, cant delete", trainerId);
        return Optional.empty();
    }

    @Override
    public Optional<Trainer> select(Integer trainerId) {
        Trainer trainer = trainerStorage.getStorage().get(trainerId);

        if (trainer != null) {
            logger.info("Trainer found successfully: {}", trainerId);
            return Optional.of(trainer);
        }

        logger.warn("Trainer with ID {} does not exist, cant be found", trainerId);
        return Optional.empty();
    }
}
