package com.mygym.crm.repositories.services;

import com.mygym.crm.domain.models.Trainee;

public interface TraineeService extends BaseService<Trainee, Integer>{

    void update(Trainee t);

    void delete(Integer id);
}
