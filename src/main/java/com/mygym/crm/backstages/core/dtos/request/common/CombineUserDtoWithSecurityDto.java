package com.mygym.crm.backstages.core.dtos.request.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CombineUserDtoWithSecurityDto<T> {

    @NotNull
    private T userDto;

}
