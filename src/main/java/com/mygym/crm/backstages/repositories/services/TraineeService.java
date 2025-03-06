package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService extends UserService<TraineeDto, Trainee>{

    void delete(SecurityDTO securityDTO, Long id);

    void deleteWithUserName(SecurityDTO securityDTO, String userName);

    List<Training> getTraineeTrainings(SecurityDTO securityDTO,String username, LocalDate fromDate,
                                       LocalDate toDate, String trainerName, String trainingTypeName);
}
