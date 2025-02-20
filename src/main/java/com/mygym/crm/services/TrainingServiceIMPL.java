package com.mygym.crm.services;

import com.mygym.crm.domain.models.Training;
import com.mygym.crm.domain.models.TrainingKey;
import com.mygym.crm.repositories.daorepositories.TrainingDAO;
import com.mygym.crm.repositories.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingServiceIMPL implements TrainingService {

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingServiceIMPL(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void create(Training training) {
        trainingDAO.create(training);
    }

    @Override
    public Optional<Training> getById(TrainingKey id) {
        return trainingDAO.select(id);
    }
}
