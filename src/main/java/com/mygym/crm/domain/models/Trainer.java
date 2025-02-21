package com.mygym.crm.domain.models;


import com.mygym.crm.domain.models.common.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Trainer extends User {
    private TrainingTypeEnum trainingType;

}

