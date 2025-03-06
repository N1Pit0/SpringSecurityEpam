package com.mygym.crm.backstages.core.dtos;

import com.mygym.crm.backstages.core.dtos.common.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class TraineeDto extends UserDto {

    private LocalDate dateOfBirth;

    private String address;
}
