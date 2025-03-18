package com.mygym.crm.backstages;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.dtos.TrainingDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.NoTraineeException;
import com.mygym.crm.backstages.exceptions.NoTrainerException;
import com.mygym.crm.backstages.exceptions.NoTrainingException;
import com.mygym.crm.backstages.repositories.services.TraineeService;
import com.mygym.crm.backstages.repositories.services.TrainerService;
import com.mygym.crm.backstages.repositories.services.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component("applicationFacade")
public class ApplicationFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationFacade.class);

    @Autowired
    public ApplicationFacade(TraineeService traineeService,
                             TrainerService trainerService,
                             TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public void createTrainee(TraineeDto traineeDTO){
        logger.info("Trying to create new trainee");
        traineeService.create(traineeDTO);
    }

    public void createTrainer(TrainerDto trainerDTO){
        logger.info("Trying to create new trainer");
        trainerService.create(trainerDTO);
    }

    public void addTraining(TrainingDto trainingDTO){
        logger.info("Trying to add new training");
        trainingService.add(trainingDTO);
    }

    public Trainee selectTrainee(SecurityDto securityDTO, Long id){
        logger.info("Trying to get a trainee by id: {}", id);
        return traineeService.getById(securityDTO,id)
                .orElseThrow(() -> {
                    logger.error("No trainee present with id: {}", id);
                    return new NoTraineeException("No trainee present with id: " + id);
                });
    }

    public Trainee selectTraineeWithUserName(SecurityDto securityDTO, String userName){
        logger.info("Trying to get a trainee by name: {}", userName);
        return traineeService.getByUserName(securityDTO, userName)
                .orElseThrow(() -> {
                    logger.error("No trainee present with name: {}", userName);
                    return new NoTraineeException("No trainee present with name: " + userName);
                });
    }

    public Trainer selectTrainer(SecurityDto securityDTO, Long id){
        logger.info("Trying to get a trainer by id: {}", id);
        return trainerService.getById(securityDTO, id)
                .orElseThrow(() -> {
                    logger.error("No trainer present with id: {}", id);
                    return new NoTrainerException("No trainer present with id: " + id);
                });
    }

    public Trainer selectTrainerWithUserName(SecurityDto securityDTO, String userName){
        logger.info("Trying to get a trainer by name: {}", userName);
        return trainerService.getByUserName(securityDTO, userName)
                .orElseThrow(() -> {
                    logger.error("No trainer present with name: {}", userName);
                    return new NoTrainerException("No trainer present with name: " + userName);
                });
    }

    public Training selectTraining(Long trainingKey){
        logger.info("Trying to get a training by trainingKey: {}", trainingKey);
        return trainingService.getById(trainingKey)
                .orElseThrow(() -> {
                    logger.error("No training present with trainingKey: {}", trainingKey);
                    return new NoTrainingException("No training present with id: " + trainingKey);
                });
    }

    public void updateTrainee(SecurityDto securityDTO, Long id, TraineeDto traineeDTO){
        logger.info("Trying to update trainee with id {}", id);
        traineeService.update(securityDTO, id, traineeDTO);
    }

    public void updateTrainer(SecurityDto securityDTO, Long id, TrainerDto trainerDTO){
        logger.info("Trying to update trainer with id {}", id);
        trainerService.update(securityDTO, id, trainerDTO);
    }

    public void deleteTrainee(SecurityDto securityDTO, Long id) {
        logger.info("Trying to delete trainee with id {}", id);
        traineeService.delete(securityDTO, id);
    }

    public void deleteTraineeWithUserName(SecurityDto securityDTO, String userName){
        logger.info("Trying to delete trainee by name: {}", userName);
        traineeService.deleteWithUserName(securityDTO, userName);
    }

    public void changePasswordForTrainee(SecurityDto securityDTO, String userName, String newPassword){
        logger.info("Trying to change password for trainee with userName {}", userName);
        traineeService.changePassword(securityDTO, userName, newPassword);
    }

    public void changePasswordForTrainer(SecurityDto securityDTO, String userName, String newPassword){
        logger.info("Trying to change password for trainer with userName {}", userName);
        trainerService.changePassword(securityDTO, userName, newPassword);
    }

    public void toggleIsActiveForTrainee(SecurityDto securityDTO, String userName){
        logger.info("Trying to toggle isActive for trainee with userName {}", userName);
        traineeService.toggleIsActive(securityDTO, userName);
    }

    public void toggleIsActiveForTrainer(SecurityDto securityDTO, String userName){
        logger.info("Trying to toggle isActive for trainer with userName {}", userName);
        trainerService.toggleIsActive(securityDTO, userName);
    }

    public List<Training> getTraineeTrainings(SecurityDto securityDTO, String username, LocalDate fromDate,
                                              LocalDate toDate, String trainerName, String trainingTypeName){
        logger.info("Trying to get traine trainings with username {}", username);
        return traineeService.getTraineeTrainings(securityDTO, username, fromDate, toDate, trainerName, trainingTypeName);
    }

    public List<Training> getTrainerTrainings(SecurityDto securityDTO, String username, LocalDate fromDate,
                                              LocalDate toDate, String traineeName){
        logger.info("Trying to get trainer trainings with username {}", username);
        return trainerService.getTrainerTrainings(securityDTO, username, fromDate, toDate, traineeName);
    }

    public List<Trainer> getTrainersNotTrainingTraineesWithUserName(SecurityDto securityDto,
                                                                    String trainerUserName, String traineeUserName){
        logger.info("Trying to get Trainers not matched with Trainee with userName {}", traineeUserName);
        return trainerService.getTrainersNotTrainingTraineesWithUserName(securityDto, trainerUserName, traineeUserName);
    }
}
