package com.mygym.crm.core.services;

import com.mygym.crm.core.services.dtos.TrainingDTO;
import com.mygym.crm.domain.models.Training;
import com.mygym.crm.domain.models.TrainingKey;
import com.mygym.crm.repositories.daorepositories.TrainingDAO;
import com.mygym.crm.repositories.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingServiceIMPL implements TrainingService<TrainingDTO>{

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingServiceIMPL(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void create(TrainingDTO trainingDTO) {
        Training newTraining = map(trainingDTO);

        trainingDAO.create(newTraining);
    }

    @Override
    public Optional<Training> getById(TrainingKey id) {
        return trainingDAO.select(id);
    }

    private Training map(TrainingDTO trainingDTO) {
        Training training = new Training();

        training.setTrainingKey(trainingDTO.getTrainingKey());
        training.setTrainingDate(trainingDTO.getTrainingDate());
        training.setTrainingName(trainingDTO.getTrainingName());
        training.setTrainingType(trainingDTO.getTrainingType());
        training.setTrainingDuration(trainingDTO.getTrainingDuration());

        return training;
    }
}
