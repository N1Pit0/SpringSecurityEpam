package com.mygym.crm.models;


public class Trainer extends User{
    public TrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }

    private TrainingTypeEnum trainingType;
}

