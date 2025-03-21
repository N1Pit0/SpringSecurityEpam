package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapSelectTrainerDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SelectTraineeDto {
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String address;

    private Boolean isActive;

    private Set<MapSelectTrainerDto> trainers;
}
