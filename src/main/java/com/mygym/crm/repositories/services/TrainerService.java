package com.mygym.crm.repositories.services;


import com.mygym.crm.domain.models.Trainer;

public interface TrainerService<T> extends BaseService<Trainer, Integer> {
    void create(T t);

    void update(Integer id, T t);

}
