package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.TrainingType;

import java.util.Optional;
import java.util.Set;

public interface TrainingTypeReadOnlyDao {
    Optional<TrainingType> getTrainingTypeByUserName(String trainingTypeName);

    Optional<Set<TrainingType>> getTrainingTypes();
}
