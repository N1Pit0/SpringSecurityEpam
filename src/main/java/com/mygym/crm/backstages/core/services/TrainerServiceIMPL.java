package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TrainerDTO;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.exceptions.NoTraineeException;
import com.mygym.crm.backstages.exceptions.NoTrainerException;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDAO;
import com.mygym.crm.backstages.repositories.services.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("trainerServiceIMPL")
public class TrainerServiceIMPL implements TrainerService<TrainerDTO>{

    private final TrainerDAO trainerDAO;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceIMPL.class);

    @Autowired
    public TrainerServiceIMPL(TrainerDAO trainerDAO, UserService userService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
    }

    @Override
    public void create(TrainerDTO trainerDTO) {
        Trainer newTrainer = map(trainerDTO);

        newTrainer.setUserId(UserService.uniqueID);
        logger.info("new Trainer set with: ID: {}", UserService.uniqueID);

        UserService.uniqueID++;

        newTrainer.setPassword(userService.generatePassword());
        logger.info("new Trainer password has been created");

        newTrainer.setUserName(userService.generateUserName(trainerDTO));
        logger.info("new Trainer userName has been created");

        logger.info("Trying to create new Trainer with ID: {}", UserService.uniqueID);
        trainerDAO.create(newTrainer);
        logger.info("new Trainer created with: ID: {}", UserService.uniqueID);
    }

    @Override
    public void update(Integer id, TrainerDTO trainerDTO) {
        Trainer oldTrainer = getById(id).orElseThrow(() -> {
            logger.error("Trainer with ID: {} not found", id);
            return new NoTraineeException("could not find Trainer with id " + id);
        });
        Trainer newTrainer = map(trainerDTO);

        logger.info("Setting with old UserId Password and UserName");
        newTrainer.setUserId(oldTrainer.getUserId());
        newTrainer.setPassword(oldTrainer.getPassword());
        newTrainer.setUserName(oldTrainer.getUserName());
        logger.info("new Trainer set with: ID: {} has been set successfully", UserService.uniqueID);

        logger.info("Trying to update Trainer with ID: {}", id);
        trainerDAO.update(newTrainer);
    }

    @Override
    public Optional<Trainer> getById(Integer id) {
        logger.info("Trying to find Trainer with ID: {}", id);
        return trainerDAO.select(id);
    }

    private Trainer map(TrainerDTO trainerDTO){
        Trainer trainer = new Trainer();
        logger.info("New Trainer, populating it with given trainerDTO");

        trainer.setFirstName(trainerDTO.getFirstName());
        trainer.setLastName(trainerDTO.getLastName());
        trainer.setActive(trainerDTO.isActive());
        trainer.setTrainingType(trainerDTO.getTrainingType());

        logger.info("New Trainer has been successfully populated with given trainerDTO");
        return trainer;
    }
}
