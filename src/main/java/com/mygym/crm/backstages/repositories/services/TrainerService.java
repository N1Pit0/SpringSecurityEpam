package com.mygym.crm.backstages.repositories.services;


import com.mygym.crm.backstages.core.dtos.TrainerDto;
import com.mygym.crm.backstages.domain.models.Trainer;

public interface TrainerService extends BaseService<Trainer, Long>, UserService<TrainerDto, Trainer> {

}
