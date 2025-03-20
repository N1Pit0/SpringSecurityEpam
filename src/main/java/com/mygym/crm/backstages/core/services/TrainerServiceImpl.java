package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.annotations.security.SecureMethod;
import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.exceptions.NoTrainerException;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingTypeReadOnlyDao;
import com.mygym.crm.backstages.repositories.services.TrainerService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service("trainerServiceIMPL")
public class TrainerServiceImpl implements TrainerService{

    private final TrainerDao trainerDAO;
    private final UserService userService;
    private final TrainingTypeReadOnlyDao trainingTypeRadOnlyDao;
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    public TrainerServiceImpl(TrainerDao trainerDAO, UserService userService, TrainingTypeReadOnlyDao trainingTypeRadOnlyDao) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
        this.trainingTypeRadOnlyDao = trainingTypeRadOnlyDao;
    }

    @Transactional
    @Override
    public Optional<Trainer> create(TrainerDto trainerDto) {
        userService.validateDto(trainerDto);

        Trainer newTrainer = map(trainerDto);
        newTrainer.setIsActive(true);

        logger.info("Trying to generate new password while attempting to create a new trainer");
        newTrainer.setPassword(userService.generatePassword());

        logger.info("Trying to generate new username while attempting to create a new trainer");
        newTrainer.setUserName(userService.generateUserName(trainerDto));

        logger.info("Trying to find and set TrainingType while attempting to create a new trainer");
        Optional<TrainingType> optionalTrainingType = trainingTypeRadOnlyDao.getTrainingTypeByUserName(trainerDto.getTrainingTypeName());

        if(optionalTrainingType.isEmpty()){
            logger.warn("TrainingType with trainingTypeName {} not found", trainerDto.getTrainingTypeName());
            return Optional.empty();
        }

        newTrainer.setTrainingType(optionalTrainingType.get());
        logger.info("TrainingType with trainingTypeName; {} has been found and set", optionalTrainingType.get().getTrainingTypeName());

        logger.info("Trying to create new trainer with UserName: {}", newTrainer.getUserName());
        Optional<Trainer> optionalTrainer = trainerDAO.create(newTrainer);

        optionalTrainer.ifPresentOrElse(
                (trainer) -> logger.info("trainer with userName: {} has been created", trainer.getUserName()),
                () -> logger.warn("trainer with userName: {} was not created", newTrainer.getUserName())
        );

        return optionalTrainer;
    }

    @Transactional
    @SecureMethod
    @Override
    public Optional<Trainer> update(SecurityDto securityDto, Long id, TrainerDto trainerDto) {
        userService.validateDto(trainerDto);

        Trainer oldTrainer = getById(securityDto, id).orElseThrow(() -> {
            logger.error("Trainer with ID: {} not found", id);
            return new NoTrainerException("could not find trainer with id " + id);
        });
        Trainer newTrainer = map(trainerDto);

        logger.info("Setting with old UserId Password and UserName");
        newTrainer.setUserId(oldTrainer.getUserId());
        newTrainer.setPassword(oldTrainer.getPassword());
        newTrainer.setUserName(oldTrainer.getUserName());

        logger.info("Trying to update Trainer with ID: {}", id);
        Optional<Trainer> optionalTrainer = trainerDAO.update(newTrainer);

        optionalTrainer.ifPresentOrElse(
                (trainer) -> logger.info("trainer with userName: {} has been updated", trainer.getUserName()),
                () -> logger.warn("trainer with userName: {} was not updated", newTrainer.getUserName())
        );

        return optionalTrainer;
    }

    @Transactional(noRollbackFor= HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Trainer> getById(SecurityDto securityDto, Long id) {
        logger.info("Trying to find Trainer with ID: {}", id);

        Optional<Trainer> trainerOptional = trainerDAO.select(id);

        trainerOptional.ifPresentOrElse(
                trainer -> {
                    trainer.getTrainings().size();
                    logger.info("Found Trainer with ID: {}", id);
                },
                () -> logger.warn("No Trainer found with ID: {}", id)
        );

        return trainerOptional;
    }

    @Transactional(noRollbackFor= HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Trainer> getByUserName(SecurityDto securityDto, String userName){
        logger.info("Trying to find Trainer with UserName: {}", userName);

        Optional<Trainer> trainerOptional = trainerDAO.selectWithUserName(userName);

        trainerOptional.ifPresentOrElse(
                trainer -> {
                    trainer.getTrainings().size();
                    logger.info("Found Trainer with UserName: {}", userName);
                },
                () -> logger.warn("No Trainer found with UserName: {}", userName)
        );
        return trainerOptional;
    }

    @Transactional
    @SecureMethod
    @Override
    public boolean changePassword(SecurityDto securityDto, String username, String newPassword) {
        logger.info("Trying to change password for Trainer with UserName: {}", username);

        boolean success = trainerDAO.changePassword(username, newPassword);

        if(success){
            logger.info("Successfully changed password for Trainer with UserName: {}", username);
            return true;
        }
        else {
            logger.warn("Failed to change password for Trainer with UserName: {}", username);
            return false;
        }
    }

    @Transactional
    @SecureMethod
    @Override
    public boolean toggleIsActive(SecurityDto securityDto, String username) {
        logger.info("Trying to toggle isActive for Trainer with UserName: {}", username);

        boolean success = trainerDAO.toggleIsActive(username);

        if(success){
            logger.info("Successfully toggled isActive for Trainer with UserName: {}", username);
            return true;
        }
        else {
            logger.warn("Failed to toggled isActive for Trainer with UserName: {}", username);
            return false;
        }
    }

    @Transactional(noRollbackFor= HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public List<Training> getTrainerTrainings(SecurityDto securityDto, String username, LocalDate fromDate,
                                              LocalDate toDate, String traineeName) {
        List<Training> trainings = trainerDAO.getTrainerTrainings(username, fromDate, toDate, traineeName);
        if(trainings.isEmpty()){
            logger.warn("No training found for Trainer with UserName: {}", username);
        }
        else logger.info("training record of size: {} was found for Trainer with UserName: {}", trainings.size(), username);
        return trainings;
    }

    @Transactional(noRollbackFor= HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public List<Trainer> getTrainersNotTrainingTraineesWithUserName(SecurityDto securityDto,
                                                                    String trainerUserName, String traineeUserName) {

        List<Trainer> trainers = trainerDAO.getTrainersNotTrainingTraineesWithUserName(traineeUserName);
        if(trainers.isEmpty()){
            logger.warn("No trainer found for Trainer with UserName: {}", trainerUserName);
        }
        else logger.info("Trainer record of size: {} was found for Trainer not matched with Trainee with username: {}",
                trainers.size(), traineeUserName);
        return trainers;
    }


    private Trainer map(TrainerDto trainerDto){
        Trainer trainer = new Trainer();
        logger.info("New Trainer, populating it with given trainerDto");

        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());

        logger.info("New Trainer has been successfully populated with given trainerDto");
        return trainer;
    }
}
