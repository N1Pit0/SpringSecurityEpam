package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.interfaces.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/trainings")
public class TrainingController {
    private TrainingService trainingService;
    private UserService userService;

    @Autowired
    public void setTrainingService(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> addTraining(@RequestBody TrainingDto trainingDto) {
        userService.validateDto(trainingDto);

        Optional<Training> optionalTraining = trainingService.add(trainingDto);

        if (optionalTraining.isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

}
