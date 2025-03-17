package com.mygym.crm.backstages.domain.models;


import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "trainer_table")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id")
public class Trainer extends User {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "training_type_id")
    @NonNull
    private TrainingType trainingType;

    @OneToMany(mappedBy = "trainer")
    private Set<Training> trainings;

}

