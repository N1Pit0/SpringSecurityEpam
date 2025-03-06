package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService extends UserService<TraineeDto, Trainee>{

    void delete(SecurityDto securityDTO, Long id);

    void deleteWithUserName(SecurityDto securityDTO, String userName);

    List<Training> getTraineeTrainings(SecurityDto securityDTO, String username, LocalDate fromDate,
                                       LocalDate toDate, String trainerName, String trainingTypeName);
}
