package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TrainingDto;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingDao;
import com.mygym.crm.backstages.repositories.services.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService<TrainingDto>{

    private final TrainingDao trainingDAO;
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void create(TrainingDto trainingDTO) {
        Training newTraining = map(trainingDTO);

        logger.info("Trying to new create training");
        trainingDAO.create(newTraining);
    }

    @Override
    public Optional<Training> getById(TrainingKey id) {
        return trainingDAO.select(id);
    }

    private Training map(TrainingDto trainingDTO) {
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
