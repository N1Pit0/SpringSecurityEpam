package com.mygym.crm.backstages.domain.models;

import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "trainee_table")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class Trainee extends User {

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String address;

    @OneToMany(mappedBy = "trainee")
    private Set<Training> trainings;

}
