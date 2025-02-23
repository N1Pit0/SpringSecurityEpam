package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.domain.models.Trainer;

public interface TrainerService<T> extends BaseService<Trainer, Integer> {
    void create(T t);

    void update(Integer id, T t);

}
