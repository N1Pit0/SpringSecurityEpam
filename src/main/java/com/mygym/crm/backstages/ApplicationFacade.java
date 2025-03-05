package com.mygym.crm.backstages;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.dtos.TrainingDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
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

@Component("applicationFacade")
public class ApplicationFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService<TrainingDto> trainingService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationFacade.class);

    @Autowired
    public ApplicationFacade(TraineeService traineeService,
                             TrainerService trainerService,
                             TrainingService<TrainingDto> trainingService) {
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

    public void createTraining(TrainingDto trainingDTO){
        logger.info("Trying to create new training");
        trainingService.create(trainingDTO);
    }

    public Trainee selectTrainee(SecurityDTO securityDTO, Long id){
        logger.info("Trying to get a trainee by id: {}", id);
        return traineeService.getById(securityDTO,id)
                .orElseThrow(() -> {
                    logger.error("No trainee present with id: {}", id);
                    return new NoTraineeException("No trainee present with id: " + id);
                });
    }

    public Trainee selectTraineeWithUserName(SecurityDTO securityDTO, String userName){
        logger.info("Trying to get a trainee by name: {}", userName);
        return traineeService.getByUserName(securityDTO, userName)
                .orElseThrow(() -> {
                    logger.error("No trainee present with name: {}", userName);
                    return new NoTraineeException("No trainee present with name: " + userName);
                });
    }

    public Trainer selectTrainer(SecurityDTO securityDTO, Long id){
        logger.info("Trying to get a trainer by id: {}", id);
        return trainerService.getById(securityDTO, id)
                .orElseThrow(() -> {
                    logger.error("No trainer present with id: {}", id);
                    return new NoTrainerException("No trainer present with id: " + id);
                });
    }

    public Training selectTraining(SecurityDTO securityDTO, Long trainingKey){
        logger.info("Trying to get a training by trainingKey: {}", trainingKey);
        return trainingService.getById(securityDTO, trainingKey)
                .orElseThrow(() -> {
                    logger.error("No training present with trainingKey: {}", trainingKey);
                    return new NoTrainingException("No training present with id: " + trainingKey);
                });
    }

    public void updateTrainee(SecurityDTO securityDTO,Long id, TraineeDto traineeDTO){
        logger.info("Trying to update trainee with id {}", id);
        traineeService.update(securityDTO, id, traineeDTO);
    }

    public void updateTrainer(SecurityDTO securityDTO,Long id, TrainerDto trainerDTO){
        logger.info("Trying to update trainer with id {}", id);
        trainerService.update(securityDTO, id, trainerDTO);
    }

    public void deleteTrainee(SecurityDTO securityDTO, Long id) {
        logger.info("Trying to delete trainee with id {}", id);
        traineeService.delete(securityDTO, id);
    }

    public void deleteTraineeWithUserName(SecurityDTO securityDTO, String userName){
        logger.info("Trying to delete trainee by name: {}", userName);
        traineeService.deleteWithUserName(securityDTO, userName);
    }
}
