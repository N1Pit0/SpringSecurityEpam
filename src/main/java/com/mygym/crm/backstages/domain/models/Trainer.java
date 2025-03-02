package com.mygym.crm.backstages.domain.models;


import com.mygym.crm.backstages.domain.models.common.User;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Trainer extends User {


    private TrainingType trainingType;

}

