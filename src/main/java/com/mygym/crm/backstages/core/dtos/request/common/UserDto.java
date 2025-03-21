package com.mygym.crm.backstages.core.dtos.request.common;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

}
