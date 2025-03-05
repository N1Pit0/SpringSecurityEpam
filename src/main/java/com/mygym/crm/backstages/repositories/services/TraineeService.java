package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.domain.models.Trainee;

public interface TraineeService extends BaseService<Trainee, Long>, UserService<TraineeDto, Trainee>{

    void delete(Long id);

    void deleteWithUserName(String userName);
}
