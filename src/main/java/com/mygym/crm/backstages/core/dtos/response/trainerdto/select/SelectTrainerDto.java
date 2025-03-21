package com.mygym.crm.backstages.core.dtos.response.trainerdto.select;

import com.mygym.crm.backstages.core.dtos.response.trainerdto.mapping.MapSelectTraineeDto;
import lombok.Data;

import java.util.Set;

@Data
public class SelectTrainerDto {

    private String firstName;

    private String lastName;

    private String trainingTypeName;

    private Boolean isActive;

    private Set<MapSelectTraineeDto> trainees;
}
