package com.mygym.crm.backstages.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TrainingType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "training_type_id")
    private Integer trainingTypeId;

    @Column(nullable = false)
    private String trainingTypeName;
}
