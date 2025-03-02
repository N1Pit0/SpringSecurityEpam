package com.mygym.crm.backstages.core.dtos;

import com.mygym.crm.backstages.domain.models.TrainingType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDto {

    private Long trainerId;

    private Long traineeId;

    private String trainingName;

    private TrainingType trainingType;

    private LocalDate trainingDate;

    private int trainingDuration;

}
