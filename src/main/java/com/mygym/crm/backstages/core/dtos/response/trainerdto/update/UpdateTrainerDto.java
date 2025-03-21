package com.mygym.crm.backstages.core.dtos.response.trainerdto.update;

import com.mygym.crm.backstages.core.dtos.response.trainerdto.mapping.MapUpdateTraineeDto;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateTrainerDto {
    private String firstName;

    private String lastName;

    private String trainingTypeName;

    private Boolean isActive;

    private Set<MapUpdateTraineeDto> trainees;
}
