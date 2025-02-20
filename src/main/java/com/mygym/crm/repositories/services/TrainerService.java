package com.mygym.crm.repositories.services;

import com.mygym.crm.domain.models.Trainer;

public interface TrainerService extends BaseService<Trainer, Integer> {
    void update(Trainer t);

}
