package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.TrainingType;

import java.util.Optional;

public interface TrainingTypeReadOnlyDao {
    Optional<TrainingType> getTrainingTypeByUserName(String trainingTypeName);
}
