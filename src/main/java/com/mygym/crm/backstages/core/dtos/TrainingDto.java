package com.mygym.crm.backstages.core.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDto {

    private Long trainerId;

    private Long traineeId;

    private String trainingName;

    private Long trainingTypeId;

    private LocalDate trainingDate;

    private int trainingDuration;

}
