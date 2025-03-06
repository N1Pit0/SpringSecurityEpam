package com.mygym.crm.backstages.core.dtos.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class UserDto {

    private String firstName;

    private String lastName;

}
