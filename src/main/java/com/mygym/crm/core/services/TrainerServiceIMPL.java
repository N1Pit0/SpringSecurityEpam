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
    private final UserService userService;

    @Autowired
    public TrainerServiceIMPL(TrainerDAO trainerDAO, UserService userService) {
        this.trainerDAO = trainerDAO;
        this.userService = userService;
    }

    @Override
    public void create(TrainerDTO trainerDTO) {
        Trainer newTrainer = map(trainerDTO);

        newTrainer.setUserId(UserService.uniqueID);
        UserService.uniqueID++;
        newTrainer.setPassword(userService.generatePassword());
        newTrainer.setUserName(userService.generateUserName(trainerDTO));

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
        trainer.setTrainingType(trainerDTO.getTrainingType());

        return trainer;
    }
}
