package com.mygym.crm.backstages.core.dtos.request.common;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import lombok.Data;

@Data
public class CombineUserDtoWithSecurityDto<T> {

    private T userDto;

    private SecurityDto securityDto;

}
