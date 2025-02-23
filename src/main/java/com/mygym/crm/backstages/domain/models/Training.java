package com.mygym.crm.backstages.domain.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Training {

    private TrainingKey trainingKey;

    private String trainingName;

    private TrainingTypeEnum trainingType;

    private LocalDate trainingDate;

    private int trainingDuration;

    // Constructors
    public Training() {}

    public Training(TrainingKey trainingKey) {
        this.trainingKey = trainingKey;
    }

}
