package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.Training;

import java.util.Optional;

public interface TrainingDao {
    Optional<Training> select(Long UserId);

    Optional<Training> create(Training training);
}
