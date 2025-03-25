package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.mygym.crm.backstages.core.dtos.response.trainingtypedto.select.SelectTrainingTypeDtoSet;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SelectTraineeTrainingsDtoSet {

    private Set<SelectTraineeTrainingsDto> traineeTrainings;

    @Data
    public static class SelectTraineeTrainingsDto {

        private String trainingName;

        private LocalDate trainingDate;

        private SelectTrainingTypeDtoSet.SelectTrainingType trainingType;

        private Integer trainingDuration;

        private String trainerName;

    }
}
