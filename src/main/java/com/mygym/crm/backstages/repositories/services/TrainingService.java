package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
import com.mygym.crm.backstages.domain.models.Training;

import java.util.Optional;

public interface TrainingService<T>{
    Optional<Training> getById(SecurityDTO securityDTO, Long id);
    void create(T t);
}
