package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.domain.models.Trainee;

public interface TraineeService <T> extends BaseService<Trainee, Integer>{
    void create(T t);

    void update(Integer id,T t);

    void delete(Integer id);
}
