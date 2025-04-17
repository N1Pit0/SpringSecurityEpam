package com.mygym.crm.backstages.core.dtos.response.trainerdto.select;

import lombok.Data;
import lombok.NonNull;

@Data
public class TrainerCredentials {

    @NonNull
    private String userName;

    @NonNull
    private String password;

}
