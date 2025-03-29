package com.mygym.crm.backstages.interfaces.services;


import com.mygym.crm.backstages.core.dtos.request.trainerdto.TrainerDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface TrainerService extends UserService<TrainerDto, Trainer> {

    Optional<Set<Training>> getTrainerTrainings(SecurityDto securityDTO, String username, LocalDate fromDate, LocalDate toDate,
                                                String traineeName);

}
