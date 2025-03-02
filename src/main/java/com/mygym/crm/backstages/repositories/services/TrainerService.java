package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.domain.models.Trainer;

public interface TrainerService<T> extends BaseService<Trainer, Long> {
    void create(T t);

    void update(Long id, T t);

}
