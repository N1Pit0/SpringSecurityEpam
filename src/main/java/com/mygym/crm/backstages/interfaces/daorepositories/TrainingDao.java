package com.mygym.crm.backstages.interfaces.daorepositories;

import com.mygym.crm.backstages.domain.models.Training;

import java.util.Optional;

public interface TrainingDao {
    Optional<Training> select(Long UserId);

    Optional<Training> add(Training training);

    int deleteWithTraineeUsername(String traineeUsername);
}
