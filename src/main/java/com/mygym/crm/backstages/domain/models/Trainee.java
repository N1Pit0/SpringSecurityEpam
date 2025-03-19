package com.mygym.crm.backstages.domain.models;

import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"trainings"})
@ToString(callSuper = true, exclude = {"trainings"})
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

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Training> trainings;

}
