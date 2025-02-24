package com.mygym.crm.backstages.domain.models;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@ToString
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

}
