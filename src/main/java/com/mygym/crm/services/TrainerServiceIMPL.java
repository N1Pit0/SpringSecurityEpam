package com.mygym.crm.services;

import com.mygym.crm.domain.models.Trainer;
import com.mygym.crm.repositories.daorepositories.TrainerDAO;
import com.mygym.crm.repositories.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerServiceIMPL implements TrainerService {

    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerServiceIMPL(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public void create(Trainer trainer) {
        trainerDAO.create(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerDAO.update(trainer);
    }

    @Override
    public Optional<Trainer> getById(Integer id) {
        return trainerDAO.select(id);
    }
}
