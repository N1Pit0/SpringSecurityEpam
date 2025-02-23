package com.mygym.crm.backstages.core.dtos;

import com.mygym.crm.backstages.core.dtos.common.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class TraineeDTO extends UserDTO {

    private LocalDate dateOfBirth;

    private String address;
}
