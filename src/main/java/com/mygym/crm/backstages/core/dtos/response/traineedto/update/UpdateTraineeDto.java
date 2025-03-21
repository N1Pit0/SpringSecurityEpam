package com.mygym.crm.backstages.core.dtos.response.traineedto.update;

import com.mygym.crm.backstages.core.dtos.response.traineedto.mapping.MapUpdateTrainerDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UpdateTraineeDto {
    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String address;

    private Boolean isActive;

    private Set<MapUpdateTrainerDto> trainers;
}
