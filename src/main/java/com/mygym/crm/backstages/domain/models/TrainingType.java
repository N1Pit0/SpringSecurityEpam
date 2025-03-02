package com.mygym.crm.backstages.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_type_id")
    private Long trainingTypeId;

    @Column(nullable = false)
    private String trainingTypeName;

    @OneToMany(mappedBy = "trainingType")
    private Set<Trainer> trainer;

    @OneToMany(mappedBy = "trainingType")
    private Set<Training> trainings;
}
