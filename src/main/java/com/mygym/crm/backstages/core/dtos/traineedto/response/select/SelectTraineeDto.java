package com.mygym.crm.backstages.core.dtos.traineedto.response.select;

import com.mygym.crm.backstages.core.dtos.trainerdto.response.select.SelectTrainerDto;
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

    private Set<SelectTrainerDto> trainers;
}
