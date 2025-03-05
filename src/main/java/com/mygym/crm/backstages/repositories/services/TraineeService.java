package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
import com.mygym.crm.backstages.domain.models.Trainee;

public interface TraineeService extends UserService<TraineeDto, Trainee>{

    void delete(SecurityDTO securityDTO, Long id);

    void deleteWithUserName(SecurityDTO securityDTO, String userName);
}
