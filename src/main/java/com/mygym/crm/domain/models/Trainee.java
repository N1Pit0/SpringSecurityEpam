package com.mygym.crm.domain.models;

import com.mygym.crm.domain.models.common.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Trainee extends User {

    private LocalDate dateOfBirth;

    private String address;

}
