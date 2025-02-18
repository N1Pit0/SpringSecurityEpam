package com.mygym.crm.models;

import java.util.Date;

public class Training {
    private int TraineeId;

    private int TrainerId;

    private String TrainingName;

    private TrainingTypeEnum trainingType;

    private Date TrainingDate;

    private int TrainingDuration;

    public int getTrainerId() {
        return TrainerId;
    }

    public void setTrainerId(int trainerId) {
        TrainerId = trainerId;
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

    public int getTraineeId() {
        return TraineeId;
    }

    public void setTraineeId(int traineeId) {
        TraineeId = traineeId;
    }
}
