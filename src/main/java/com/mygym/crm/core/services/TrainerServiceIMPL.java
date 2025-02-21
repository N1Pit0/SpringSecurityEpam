package com.mygym.crm.core.services;

import com.mygym.crm.core.services.dtos.TrainerDTO;
import com.mygym.crm.domain.models.Trainee;
import com.mygym.crm.domain.models.Trainer;
import com.mygym.crm.exceptions.NoTrainerException;
import com.mygym.crm.repositories.daorepositories.TrainerDAO;
import com.mygym.crm.repositories.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerServiceIMPL implements TrainerService<TrainerDTO>{

    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerServiceIMPL(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public void create(TrainerDTO trainerDTO) {
        Trainer newTrainer = map(trainerDTO);
        newTrainer.setUserId(1); //ID generation is not implemented yet
        //password and username as well

        trainerDAO.create(newTrainer);
    }

    @Override
    public void update(Integer id, TrainerDTO trainerDTO) {
        Trainer oldTrainer = getById(id).orElseThrow(() -> new NoTrainerException("could not find trainer with id \" + id"));
        Trainer newTrainer = map(trainerDTO);

        newTrainer.setTrainingType(oldTrainer.getTrainingType());
        newTrainer.setUserId(oldTrainer.getUserId());
        newTrainer.setPassword(oldTrainer.getPassword());
        newTrainer.setUserName(oldTrainer.getUserName());

        trainerDAO.update(newTrainer);
    }

    @Override
    public Optional<Trainer> getById(Integer id) {
        return trainerDAO.select(id);
    }

    private Trainer map(TrainerDTO trainerDTO){
        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerDTO.getFirstName());
        trainer.setLastName(trainerDTO.getLastName());
        trainer.setActive(trainerDTO.isActive());

        return trainer;
    }
}
