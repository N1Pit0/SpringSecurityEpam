package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TrainingDTO;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingDAO;
import com.mygym.crm.backstages.repositories.services.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingServiceIMPL implements TrainingService<TrainingDTO>{

    private final TrainingDAO trainingDAO;
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceIMPL.class);

    @Autowired
    public TrainingServiceIMPL(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void create(TrainingDTO trainingDTO) {
        Training newTraining = map(trainingDTO);

        logger.info("Trying to new create training");
        trainingDAO.create(newTraining);
        logger.info("new Trainee created with: ID: {}", trainingDTO.getTrainingKey().toString());
    }

    @Override
    public Optional<Training> getById(TrainingKey id) {
        return trainingDAO.select(id);
    }

    private Training map(TrainingDTO trainingDTO) {
        Training training = new Training();
        logger.info("New Training, populating it with given traineeDTO");

        training.setTrainingKey(trainingDTO.getTrainingKey());
        training.setTrainingDate(trainingDTO.getTrainingDate());
        training.setTrainingName(trainingDTO.getTrainingName());
        training.setTrainingType(trainingDTO.getTrainingType());
        training.setTrainingDuration(trainingDTO.getTrainingDuration());

        logger.info("New Training has been successfully populated with given traineeDTO");
        return training;
    }
}
