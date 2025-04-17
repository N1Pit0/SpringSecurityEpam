package com.mygym.crm.backstages.core.dtos.response.traineedto.select;

import lombok.Data;
import lombok.NonNull;

@Data
public class TraineeCredentialsDto {

    @NonNull
    private String userName;

    @NonNull
    private String password;

}
