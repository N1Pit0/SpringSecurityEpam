package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.exceptions.NoTraineeException;
import com.mygym.crm.backstages.repositories.services.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/trainees")
public class TraineeController {
    private TraineeService traineeService;

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<String> getTraineeService() {
        return new ResponseEntity<>("getgetget", HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SecurityDto> registerTrainee(@RequestBody TraineeDto trainee) {
        Optional<Trainee> optionalTrainee = traineeService.create(trainee);

        optionalTrainee.ifPresentOrElse((tr -> System.out.println(tr.getUserName())),
                () -> System.out.println("not"));

        Trainee trainee1 = optionalTrainee.orElseThrow(() -> new NoTraineeException("no trainee"));


        return new ResponseEntity<>(new SecurityDto(trainee1.getUserName(), trainee1.getPassword())
                                ,HttpStatus.CREATED);
    }

//    @PostMapping(consumes = "application/json", produces = "application/json")
//    public ResponseEntity<String> registerTrainee(@RequestBody String jsonBody) {
//        System.out.println("registerTrainee method called!");
//        System.out.println("Received Body: " + jsonBody);
//
//        return new ResponseEntity<>("Success", HttpStatus.OK);
//    }

}
