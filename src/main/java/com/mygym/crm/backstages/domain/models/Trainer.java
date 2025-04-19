package com.mygym.crm.backstages.domain.models;


import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true, exclude = {"trainings"})
@ToString(callSuper = true, exclude = {"trainings"})
@Data
@Entity
@Table(name = "trainer_table")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class Trainer extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType trainingType;

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainings;

}

