package com.mygym.crm.repositories.services;

import com.mygym.crm.domain.models.Training;
import com.mygym.crm.domain.models.TrainingKey;

public interface TrainingService<T> extends BaseService<Training, TrainingKey>{
    void create(T t);
}
