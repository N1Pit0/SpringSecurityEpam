package com.mygym.crm.backstages.core.dtos.request.trainingdto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TrainingDto {

    @NotNull
    private Long trainerId;

    @NotNull
    private Long traineeId;

    @NotNull
    private String trainingName;

    @NotNull
    private String trainingTypeName;

    @NotNull
    private LocalDate trainingDate;

    @NotNull
    private int trainingDuration;

}
