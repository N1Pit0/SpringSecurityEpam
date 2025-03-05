package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.Annotations.SecutiryAnnotations.SecureMethod;
import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.exceptions.NoTrainerException;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import com.mygym.crm.backstages.repositories.services.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("trainerServiceIMPL")
public class TrainerServiceImpl implements TrainerService{

    private final TrainerDao trainerDAO;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    public TrainerServiceImpl(TrainerDao trainerDAO, UserService userService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
    }

    @Override
    public void create(TrainerDto trainerDTO) {
        Trainer newTrainer = map(trainerDTO);

        logger.info("Trying to generate new password while attempting to create a new trainer");
        newTrainer.setPassword(userService.generatePassword());

        logger.info("Trying to generate new username while attempting to create a new trainer");
        newTrainer.setUserName(userService.generateUserName(trainerDTO));

        logger.info("Trying to create new Trainer with ID: {}", newTrainer.getUserId());
        trainerDAO.create(newTrainer);
    }

    @SecureMethod
    @Override
    public void update(SecurityDTO securityDTO,Long id, TrainerDto trainerDTO) {
        Trainer oldTrainer = getById(securityDTO, id).orElseThrow(() -> {
            logger.error("Trainer with ID: {} not found", id);
            return new NoTrainerException("could not find Trainer with id " + id);
        });
        Trainer newTrainer = map(trainerDTO);

        logger.info("Setting with old UserId Password and UserName");
        newTrainer.setUserId(oldTrainer.getUserId());
        newTrainer.setPassword(oldTrainer.getPassword());
        newTrainer.setUserName(oldTrainer.getUserName());
//        logger.info("new Trainer set with: ID: {} has been set successfully", UserService.uniqueID);

        logger.info("Trying to update Trainer with ID: {}", id);
        trainerDAO.update(newTrainer);
    }

    @SecureMethod
    @Override
    public Optional<Trainer> getById(SecurityDTO securityDTO, Long id) {
        logger.info("Trying to find Trainer with ID: {}", id);
        return trainerDAO.select(id);
    }

    @SecureMethod
    @Override
    public Optional<Trainer> getByUserName(SecurityDTO securityDTO, String userName) {
        return Optional.empty();
    }

    private Trainer map(TrainerDto trainerDTO){
        Trainer trainer = new Trainer();
        logger.info("New Trainer, populating it with given trainerDTO");

        trainer.setFirstName(trainerDTO.getFirstName());
        trainer.setLastName(trainerDTO.getLastName());
        trainer.setIsActive(trainerDTO.isActive());
        trainer.setTrainingType(trainerDTO.getTrainingType());

        logger.info("New Trainer has been successfully populated with given trainerDTO");
        return trainer;
    }
}
