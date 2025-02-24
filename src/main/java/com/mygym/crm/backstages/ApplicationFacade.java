package com.mygym.crm.backstages;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.dtos.TrainingDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
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
    private final TraineeService<TraineeDto> traineeService;
    private final TrainerService<TrainerDto> trainerService;
    private final TrainingService<TrainingDto> trainingService;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationFacade.class);

    @Autowired
    public ApplicationFacade(TraineeService<TraineeDto> traineeService,
                             TrainerService<TrainerDto> trainerService,
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

    public Trainee selectTrainee(Integer id){
        logger.info("Trying to get a trainee by id: {}", id);
        return traineeService.getById(id)
                .orElseThrow(() -> {
                    logger.error("No trainee present with id: {}", id);
                    return new NoTraineeException("No trainee present with id: " + id);
                });
    }

    public Trainer selectTrainer(Integer id){
        logger.info("Trying to get a trainer by id: {}", id);
        return trainerService.getById(id)
                .orElseThrow(() -> {
                    logger.error("No trainer present with id: {}", id);
                    return new NoTrainerException("No trainer present with id: " + id);
                });
    }

    public Training selectTraining(TrainingKey trainingKey){
        logger.info("Trying to get a training by trainingKey: {}", trainingKey);
        return trainingService.getById(trainingKey)
                .orElseThrow(() -> {
                    logger.error("No training present with trainingKey: {}", trainingKey);
                    return new NoTrainingException("No training present with id: " + trainingKey);
                });
    }

    public void updateTrainee(Integer id, TraineeDto traineeDTO){
        logger.info("Trying to update trainee with id {}", id);
        traineeService.update(id, traineeDTO);
    }

    public void updateTrainer(Integer id, TrainerDto trainerDTO){
        logger.info("Trying to update trainer with id {}", id);
        trainerService.update(id, trainerDTO);
    }

    public void deleteTrainee(Integer id) {
        logger.info("Trying to delete trainee with id {}", id);
        traineeService.delete(id);
    }
}
