package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TrainingDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingDao;
import com.mygym.crm.backstages.repositories.services.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService<TrainingDto>{

    private final TrainingDao trainingDAO;
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;

    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDAO, TrainerDao trainerDao, TraineeDao traineeDao) {
        this.trainingDAO = trainingDAO;
        this.trainerDao = trainerDao;
        this.traineeDao = traineeDao;
    }

    @Transactional
    @Override
    public void add(TrainingDto trainingDto) {
        Training newTraining = new Training();
        logger.info("New Training, populating it with given traineeDTO");

        newTraining.setTrainingName(trainingDto.getTrainingName());
        newTraining.setTrainingDate(trainingDto.getTrainingDate());
        newTraining.setTrainingDuration(trainingDto.getTrainingDuration());

        Trainer trainer = trainerDao.select(trainingDto.getTrainerId()).orElse(null);
        Trainee trainee = traineeDao.select(trainingDto.getTraineeId()).orElse(null);

        newTraining.setTrainer(trainer);
        newTraining.setTrainee(trainee);

        logger.info("Trying to new create training");
        trainingDAO.add(newTraining, trainingDto.getTrainingTypeId());
    }

    @Transactional
    @Override
    public Optional<Training> getById(Long id) {
        logger.info("Trying to find Training with ID: {}", id);

        Optional<Training> trainingOptional = trainingDAO.select(id);

        trainingOptional.ifPresentOrElse(
                training -> {
                    training.getTrainee().getUserId();
                    training.getTrainer().getUserId();
                    training.getTrainingType().getTrainingTypeId();
                    logger.info("Found Training with ID: {}", id);
                },
                () -> logger.warn("No training found with ID: {}", id)
        );

        return trainingOptional;
    }

}
