package com.mygym.crm.domain.models;

import lombok.Data;

import java.util.Date;

@Data
public class Training {

    private TrainingKey trainingKey;

    private String trainingName;

    private TrainingTypeEnum trainingType;

    private Date trainingDate;

    private int trainingDuration;

    // Constructors
    public Training() {}

    public Training(TrainingKey trainingKey) {
        this.trainingKey = trainingKey;
    }

}
