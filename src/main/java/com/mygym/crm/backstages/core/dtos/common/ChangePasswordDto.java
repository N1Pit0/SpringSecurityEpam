package com.mygym.crm.backstages.core.dtos.common;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDto {

    @NotNull
    private String oldPassword;

    @NotNull
    private String newPassword;
}
