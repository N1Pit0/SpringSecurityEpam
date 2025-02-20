package com.mygym.crm.domain.models;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Trainer extends User {
    private TrainingTypeEnum trainingType;

    private int TrainerId;

}

