package com.mygym.crm.core.dtos;

import com.mygym.crm.domain.models.TrainingKey;
import com.mygym.crm.domain.models.TrainingTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDTO{

    private TrainingKey trainingKey;

    private String trainingName;

    private TrainingTypeEnum trainingType;

    private LocalDate trainingDate;

    private int trainingDuration;

}
