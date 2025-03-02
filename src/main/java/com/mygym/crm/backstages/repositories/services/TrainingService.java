package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.domain.models.Training;

public interface TrainingService<T> extends BaseService<Training, Long>{
    void create(T t);
}
