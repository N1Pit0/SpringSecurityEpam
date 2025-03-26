package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.annotations.security.SecureMethod;
import com.mygym.crm.backstages.core.dtos.request.trainerdto.TrainerDto;
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
import java.util.Set;

@Service("trainerServiceIMPL")
public class TrainerServiceImpl implements TrainerService {

    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private final TrainerDao trainerDao;
    private final UserService userService;
    private final TrainingTypeReadOnlyDao trainingTypeRadOnlyDao;

    @Autowired
    public TrainerServiceImpl(TrainerDao trainerDao, UserService userService, TrainingTypeReadOnlyDao trainingTypeRadOnlyDao) {
        this.trainerDao = trainerDao;
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

        if (optionalTrainingType.isEmpty()) {
            logger.warn("TrainingType with trainingTypeName {} not found", trainerDto.getTrainingTypeName());
            return Optional.empty();
        }

        newTrainer.setTrainingType(optionalTrainingType.get());
        logger.info("TrainingType with trainingTypeName; {} has been found and set", optionalTrainingType.get().getTrainingTypeName());

        logger.info("Trying to create new trainer with UserName: {}", newTrainer.getUserName());
        Optional<Trainer> optionalTrainer = trainerDao.create(newTrainer);

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
        newTrainer.setTrainings(oldTrainer.getTrainings());

        logger.info("Trying to find and set TrainingType while attempting to update trainer for update");
        Optional<TrainingType> optionalTrainingType = trainingTypeRadOnlyDao.getTrainingTypeByUserName(trainerDto.getTrainingTypeName());

        if (optionalTrainingType.isEmpty()) {
            logger.warn("TrainingType with trainingTypeName {} not found for updateByUserName for update", trainerDto.getTrainingTypeName());
            return Optional.empty();
        }

        logger.info("TrainingType with trainingTypeName {} has been found for updateByUserName for update", trainerDto.getTrainingTypeName());
        newTrainer.setTrainingType(optionalTrainingType.get());

        logger.info("Trying to update Trainer with ID: {}", id);
        Optional<Trainer> optionalTrainer = trainerDao.update(newTrainer);

        optionalTrainer.ifPresentOrElse(
                (trainer) -> logger.info("trainer with userName: {} has been updated", trainer.getUserName()),
                () -> logger.warn("trainer with userName: {} was not updated", newTrainer.getUserName())
        );

        return optionalTrainer;
    }

    @Transactional
    @SecureMethod
    @Override
    public Optional<Trainer> updateByUserName(SecurityDto securityDto, String userName, TrainerDto trainerDto) {
        userService.validateDto(trainerDto);

        Trainer oldTrainer = getByUserName(securityDto, userName).orElseThrow(() -> {
            logger.error("Trainer with UserName {} not found", userName);
            return new NoTrainerException("could not find trainer with UserName " + userName);
        });
        Trainer newTrainer = map(trainerDto);

        logger.info("Setting with old UserId Password and UserName inside updateByUserName for Treainer");
        newTrainer.setUserId(oldTrainer.getUserId());
        newTrainer.setPassword(oldTrainer.getPassword());
        newTrainer.setUserName(oldTrainer.getUserName());
        newTrainer.setIsActive(oldTrainer.getIsActive());
        newTrainer.setTrainings(oldTrainer.getTrainings());

        logger.info("Trying to find and set TrainingType while attempting to update trainer");
        Optional<TrainingType> optionalTrainingType = trainingTypeRadOnlyDao.getTrainingTypeByUserName(trainerDto.getTrainingTypeName());

        if (optionalTrainingType.isEmpty()) {
            logger.warn("TrainingType with trainingTypeName {} not found for updateByUserName", trainerDto.getTrainingTypeName());
            return Optional.empty();
        }

        logger.info("TrainingType with trainingTypeName {} has been found for updateByUserName", trainerDto.getTrainingTypeName());
        newTrainer.setTrainingType(optionalTrainingType.get());

        logger.info("Trying to update Trainee with userName: {}", userName);
        Optional<Trainer> optionalTrainer = trainerDao.update(newTrainer);

        optionalTrainer.ifPresentOrElse(
                (trainee) -> {
                    logger.info("trainee with userName: {} has been updated", trainee.getUserName());
                    trainee.getTrainings().size();
                },
                () -> logger.warn("trainee with userName: {} was not updated", newTrainer.getUserName())
        );

        return optionalTrainer;
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Trainer> getById(SecurityDto securityDto, Long id) {
        logger.info("Trying to find Trainer with ID: {}", id);

        Optional<Trainer> trainerOptional = trainerDao.select(id);

        trainerOptional.ifPresentOrElse(
                trainer -> {
                    trainer.getTrainings().size();
                    logger.info("Found Trainer with ID: {}", id);
                },
                () -> logger.warn("No Trainer found with ID: {}", id)
        );

        return trainerOptional;
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Trainer> getByUserName(SecurityDto securityDto, String userName) {
        logger.info("Trying to find Trainer with UserName: {}", userName);

        Optional<Trainer> trainerOptional = trainerDao.selectWithUserName(userName);

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

        boolean success = trainerDao.changePassword(username, newPassword);

        if (success) {
            logger.info("Successfully changed password for Trainer with UserName: {}", username);
            return true;
        } else {
            logger.warn("Failed to change password for Trainer with UserName: {}", username);
            return false;
        }
    }

    @Transactional
    @SecureMethod
    @Override
    public boolean toggleIsActive(SecurityDto securityDto, String username) {
        logger.info("Trying to toggle isActive for Trainer with UserName: {}", username);

        boolean success = trainerDao.toggleIsActive(username);

        if (success) {
            logger.info("Successfully toggled isActive for Trainer with UserName: {}", username);
            return true;
        } else {
            logger.warn("Failed to toggled isActive for Trainer with UserName: {}", username);
            return false;
        }
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Set<Training>> getTrainerTrainings(SecurityDto securityDto, String username, LocalDate fromDate,
                                                       LocalDate toDate, String traineeName) {
        Set<Training> trainings = trainerDao.getTrainerTrainings(username, fromDate, toDate, traineeName);
        if (trainings.isEmpty()) {
            logger.warn("No training found for Trainer with UserName: {}", username);
        } else
            logger.info("training record of size: {} was found for Trainer with UserName: {}", trainings.size(), username);
        return Optional.of(trainings);
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Set<Trainer>> getTrainersNotTrainingTraineesWithUserName(SecurityDto securityDto,
                                                                    String traineeUserName) {

        Set<Trainer> trainers = trainerDao.getTrainersNotTrainingTraineesWithUserName(traineeUserName);
        if (trainers.isEmpty()) {
            logger.warn("No unassigned trainers found");
        } else
            logger.info("Trainer record of size: {} was found for Trainer not matched with Trainee with username: {}",
                    trainers.size(), traineeUserName);
        return Optional.of(trainers);
    }


    private Trainer map(TrainerDto trainerDto) {
        Trainer trainer = new Trainer();
        logger.info("New Trainer, populating it with given trainerDto");

        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());

        logger.info("New Trainer has been successfully populated with given trainerDto");
        return trainer;
    }
}
