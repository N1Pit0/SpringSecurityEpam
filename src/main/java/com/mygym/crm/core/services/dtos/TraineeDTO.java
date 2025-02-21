package com.mygym.crm.core.services.dtos;

import com.mygym.crm.core.services.dtos.common.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class TraineeDTO extends UserDTO{

    private LocalDate dateOfBirth;

    private String address;
}
