package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;

public interface TrainingService<T> extends BaseService<Training, TrainingKey>{
    void create(T t);
}
