package com.mygym.crm.domain.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public abstract class User {

    @Setter
    @Getter
    private int userId;

    @Setter
    @Getter
    private String firstName;

    @Getter
    @Setter
    private String lastName;

    @Getter
    private String userName;

    @Getter
    private String password;

    @Setter
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

}
