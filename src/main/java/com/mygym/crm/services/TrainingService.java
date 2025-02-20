package com.mygym.crm.services;

import com.mygym.crm.models.Training;
import com.mygym.crm.models.TrainingKey;
import com.mygym.crm.repositories.daorepositories.TrainingDAO;
import com.mygym.crm.repositories.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingService implements BaseService<Training, TrainingKey> {

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void create(Training training) {
        trainingDAO.create(training);
    }

    @Override
    public void update(Training training) {

    }

    @Override
    public void delete(TrainingKey id) {

    }

    @Override
    public Optional<Training> getById(TrainingKey id) {
        return trainingDAO.select(id);
    }
}
