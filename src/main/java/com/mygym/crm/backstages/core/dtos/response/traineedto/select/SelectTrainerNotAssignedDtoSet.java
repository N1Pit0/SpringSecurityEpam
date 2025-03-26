package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapSelectTrainerDto;
import lombok.Data;

import java.util.Set;

@Data
public class SelectTrainerNotAssignedDtoSet {

    private Set<MapSelectTrainerDto> notAssignedTrainers;

}