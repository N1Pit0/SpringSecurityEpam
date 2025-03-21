package com.mygym.crm.backstages.core.dtos.request.traineedto;

import com.mygym.crm.backstages.core.dtos.request.common.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class TraineeDto extends UserDto {

    private LocalDate dateOfBirth;

    private String address;
}
