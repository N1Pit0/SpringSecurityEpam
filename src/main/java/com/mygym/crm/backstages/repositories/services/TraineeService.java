package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.domain.models.Trainee;

public interface TraineeService <T> extends BaseService<Trainee, Long>{
    void create(T t);

    void update(Long id,T t);

    void delete(Long id);
}
