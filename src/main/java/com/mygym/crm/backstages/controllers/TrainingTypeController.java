package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.mapper.TrainingTypeMapper;
import com.mygym.crm.backstages.repositories.services.TrainingTypeRadOnlyService;
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
    public ResponseEntity<SelectTrainingTypeDtoSet> getTrainingTypes() {

        Optional<Set<TrainingType>> optionalTrainingTypes = trainingTypeRadOnlyService.getTrainingType();

        return optionalTrainingTypes
                .map(trainingTypeMapper::toTrainingTypeDtoSet)
                .map((trainingTypes) -> new ResponseEntity<>(trainingTypes, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.valueOf(405)));
    }
}
