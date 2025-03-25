package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.core.dtos.request.traineedto.TraineeDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface TraineeService extends UserService<TraineeDto, Trainee> {

    Optional<Trainee> delete(SecurityDto securityDTO, Long id);

    Optional<Trainee> deleteWithUserName(SecurityDto securityDTO, String userName);

    Optional<Set<Training>> getTraineeTrainings(SecurityDto securityDTO, String username, LocalDate fromDate,
                                                LocalDate toDate, String trainerName, String trainingTypeName);

}
