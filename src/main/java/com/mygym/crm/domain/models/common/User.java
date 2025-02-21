package com.mygym.crm.domain.models.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public abstract class User {

    private int userId;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private boolean isActive;

}
