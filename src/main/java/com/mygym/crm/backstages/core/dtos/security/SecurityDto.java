package com.mygym.crm.backstages.core.dtos.security;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SecurityDto {

    private String userName;

    private String password;

}
