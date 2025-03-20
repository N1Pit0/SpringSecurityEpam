package com.mygym.crm.backstages.core.dtos.traineedto.response.update;

import com.mygym.crm.backstages.core.dtos.trainerdto.response.update.UpdateTrainerDto;
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

    private Set<UpdateTrainerDto> trainers;
}
