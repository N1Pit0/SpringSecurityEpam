package com.mygym.crm.backstages.core.dtos;

import com.mygym.crm.backstages.domain.models.TrainingKey;
import com.mygym.crm.backstages.domain.models.TrainingTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDto {

    private TrainingKey trainingKey;

    private String trainingName;

    private TrainingTypeEnum trainingType;

    private LocalDate trainingDate;

    private int trainingDuration;

}
