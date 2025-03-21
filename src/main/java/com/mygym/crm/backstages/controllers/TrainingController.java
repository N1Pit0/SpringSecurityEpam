package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.repositories.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private TrainingService trainingService;

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> addTraining(@RequestBody TrainingDto trainingDto) {

        Optional<Training> optionalTraining = trainingService.add(trainingDto);

        if (optionalTraining.isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}
