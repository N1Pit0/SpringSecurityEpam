package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
import com.mygym.crm.backstages.core.services.UserService;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.ResourceCreationException;
import com.mygym.crm.backstages.interfaces.services.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "Create new Training", description = "Creates new Training and save it in new database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created new Training"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "409", description = "The Training resource is not there OR could not perform the action"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<Void> addTraining(@RequestBody TrainingDto trainingDto) {
        userService.validateDto(trainingDto);

        Optional<Training> optionalTraining = trainingService.add(trainingDto);

        if (optionalTraining.isPresent()) {
            return ResponseEntity.ok().build();
        }

        throw new ResourceCreationException("could not create Training");
    }

}
