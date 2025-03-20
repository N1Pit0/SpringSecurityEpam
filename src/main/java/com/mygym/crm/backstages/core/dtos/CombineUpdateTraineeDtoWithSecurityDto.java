package com.mygym.crm.backstages.core.dtos;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import lombok.Data;

@Data
public class CombineUpdateTraineeDtoWithSecurityDto {

    private TraineeDto traineeDto;

    private SecurityDto securityDto;

}
