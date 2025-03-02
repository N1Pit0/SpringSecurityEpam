package com.mygym.crm.backstages.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private TrainingKey trainingKey;

    @Column(nullable = false)
    private String trainingName;

    private TrainingType trainingType;

    @Column(nullable = false)

    private LocalDate trainingDate;

    @Column(nullable = false)
    private Integer trainingDuration;

    // Constructors

    public Training(TrainingKey trainingKey) {
        this.trainingKey = trainingKey;
    }

}
