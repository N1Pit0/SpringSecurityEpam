package com.mygym.crm.models;


public class Trainer extends User {
    private TrainingTypeEnum trainingType;
    private int TrainerId;

    public TrainingTypeEnum getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingTypeEnum trainingType) {
        this.trainingType = trainingType;
    }

    public int getTrainerId() {
        return TrainerId;
    }

    public void setTrainerId(int trainerId) {
        TrainerId = trainerId;
    }
}

