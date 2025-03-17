package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.exceptions.NoTrainerException;
import com.mygym.crm.backstages.repositories.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController(value = "/trainers")
public class TrainerController {
    private TrainerService trainerService;

    @Autowired
    public void setTrainerService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SecurityDto> registerTrainer(@RequestBody TrainerDto trainerDto) {
        System.out.println("I am here ");
        Optional<Trainer> optionalTrainer = trainerService.create(trainerDto);

        Trainer trainer1 = optionalTrainer.orElseThrow(() -> new NoTrainerException("no trainer"));

        return new ResponseEntity<>(new SecurityDto(trainer1.getUserName(), trainer1.getPassword()),
                HttpStatus.CREATED);
    }
}
