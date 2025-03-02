package com.mygym.crm.backstages.domain.models;

import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
public class Trainee extends User {

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String address;

}
