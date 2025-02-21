package com.mygym.crm.core.services.dtos;

import com.mygym.crm.domain.models.TrainingKey;
import com.mygym.crm.domain.models.TrainingTypeEnum;
import lombok.Data;

import java.util.Date;

@Data
public class TrainingDTO{

    private TrainingKey trainingKey;

    private String trainingName;

    private TrainingTypeEnum trainingType;

    private Date trainingDate;

    private int trainingDuration;

}
