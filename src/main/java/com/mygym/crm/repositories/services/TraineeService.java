package com.mygym.crm.repositories.services;


import com.mygym.crm.domain.models.Trainee;

public interface TraineeService <T> extends BaseService<Trainee, Integer>{
    void create(T t);

    void update(Integer id,T t);

    void delete(Integer id);
}
