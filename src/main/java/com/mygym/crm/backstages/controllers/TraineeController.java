package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.common.ChangePasswordDto;
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
@RequestMapping(value = "/users/trainees")
public class TraineeController {
    private TraineeService traineeService;

    @Autowired
    public void setTraineeService(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping(value = "{userName:.+}", produces = "application/json")
    public ResponseEntity<Trainee> getTraineeProfile(@PathVariable("userName") String userName,
                        @RequestBody SecurityDto securityDto) throws NoTraineeException {

        Optional<Trainee> optionalTrainee = traineeService.getByUserName(securityDto,userName);

        if (optionalTrainee.isPresent()) {
            Trainee trainee = optionalTrainee.get();
            return new ResponseEntity<>(trainee, HttpStatus.FOUND);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<SecurityDto> registerTrainee(@RequestBody TraineeDto trainee) {
        System.out.println("I am here ");

        Optional<Trainee> optionalTrainee = traineeService.create(trainee);

        Trainee trainee1 = optionalTrainee.orElseThrow(() -> new NoTraineeException("no trainee"));

        return new ResponseEntity<>(new SecurityDto(trainee1.getUserName(), trainee1.getPassword()),
                HttpStatus.CREATED);
    }

    @PutMapping(value = "/{userName:.+}/change-login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> changeLogin(@PathVariable("userName")String userName,
            @RequestBody ChangePasswordDto changePasswordDto){

        boolean isPassed = traineeService.changePassword(
                new SecurityDto(userName, changePasswordDto.getOldPassword()),
                userName,
                changePasswordDto.getNewPassword()
        );

        if(isPassed) return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

}
