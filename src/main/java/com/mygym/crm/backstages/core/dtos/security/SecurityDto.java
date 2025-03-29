package com.mygym.crm.backstages.core.dtos.security;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class SecurityDto {

    @NonNull // lombok`s NonNull
    @NotNull
    private String userName;

    @NonNull // lombok`s NonNull
    @NotNull
    private String password;

}
