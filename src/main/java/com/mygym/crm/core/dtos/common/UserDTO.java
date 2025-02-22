package com.mygym.crm.core.dtos.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class UserDTO {

    private String firstName;

    private String lastName;

    private boolean isActive;

}
