package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.exceptions.custom.NoResourceException;
import com.mygym.crm.backstages.interfaces.services.TrainingTypeRadOnlyService;
import com.mygym.crm.backstages.mapper.TrainingTypeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/training-types")
public class TrainingTypeController {

    private TrainingTypeRadOnlyService trainingTypeRadOnlyService;
    private TrainingTypeMapper trainingTypeMapper;

    @Autowired
    public void setTrainingTypeRadOnlyService(TrainingTypeRadOnlyService trainingTypeRadOnlyService) {
        this.trainingTypeRadOnlyService = trainingTypeRadOnlyService;
    }

    @Autowired
    public void setTrainingTypeMapper(TrainingTypeMapper trainingTypeMapper) {
        this.trainingTypeMapper = trainingTypeMapper;
    }

    @GetMapping("")
    @Operation(summary = "List Training Types", description = "Lists all Training Types from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched the list of Training Types"),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found"),
            @ApiResponse(responseCode = "409", description = "The Training Types resource is not there OR could not perform the action"),
            @ApiResponse(responseCode = "500", description = "Application failed to process the request")
    })
    public ResponseEntity<SelectTrainingTypeDtoSet> getTrainingTypes() {

        Optional<Set<TrainingType>> optionalTrainingTypes = trainingTypeRadOnlyService.getTrainingType();

        return optionalTrainingTypes
                .map(trainingTypeMapper::toTrainingTypeDtoSet)
                .map((trainingTypes) -> new ResponseEntity<>(trainingTypes, HttpStatus.OK))
                .orElseThrow(() -> new NoResourceException("No training types found"));
    }
}
