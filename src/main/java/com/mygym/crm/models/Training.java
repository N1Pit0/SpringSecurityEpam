package com.mygym.crm.models;

import jakarta.persistence.Embedded;

import java.util.Date;

public class Training {

    @Embedded
    private TrainingKey trainingKey;

    private String TrainingName;

    private TrainingTypeEnum trainingType;

    private Date TrainingDate;

    private int TrainingDuration;

    // Constructors
    public Training() {}

    public Training(TrainingKey trainingKey) {
        this.trainingKey = trainingKey;
    }

    // Getters and setters
    public TrainingKey getTrainingKey() {
        return trainingKey;
    }

    public void setId(TrainingKey trainingKey) {
        this.trainingKey = trainingKey;
    }

    public String getTrainingName() {
        return TrainingName;
    }

    public void setTrainingName(String trainingName) {
        TrainingName = trainingName;
    }

    public TrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }

    public Date getTrainingDate() {
        return TrainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        TrainingDate = trainingDate;
    }

    public int getTrainingDuration() {
        return TrainingDuration;
    }

    public void setTrainingDuration(int trainingDuration) {
        TrainingDuration = trainingDuration;
    }

}
