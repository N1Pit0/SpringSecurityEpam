package com.mygym.crm.domain.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TrainingKey implements Serializable {
    private Integer traineeId;
    private Integer trainerId;

    public TrainingKey() {

    }

    public TrainingKey(Integer traineeId, Integer trainerId) {
        this.traineeId = traineeId;
        this.trainerId = trainerId;
    }

    public Integer getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Integer traineeId) {
        this.traineeId = traineeId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingKey that)) return false;
        return Objects.equals(traineeId, that.traineeId) && Objects.equals(trainerId, that.trainerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traineeId, trainerId);
    }
}
