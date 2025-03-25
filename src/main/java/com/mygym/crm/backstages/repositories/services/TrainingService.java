package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
import com.mygym.crm.backstages.domain.models.Training;

import java.util.Optional;

public interface TrainingService {
    Optional<Training> getById(Long id);

    Optional<Training> add(TrainingDto t);
}
