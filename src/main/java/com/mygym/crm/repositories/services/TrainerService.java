package com.mygym.crm.repositories.services;

import com.mygym.crm.models.Trainer;

public interface TrainerService extends BaseService<Trainer, Integer> {
    void update(Trainer t);

}
