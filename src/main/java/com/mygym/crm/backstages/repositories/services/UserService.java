package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.core.dtos.request.trainerdto.TrainerDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.Trainer;

import java.util.Optional;

public interface UserService <T, E>{

    Optional<E> create(T t);

    Optional<E> update(SecurityDto securityDTO, Long id, T t);

    Optional<E> getByUserName(SecurityDto securityDTO, String userName);

    Optional<E> getById(SecurityDto securityDTO, Long id);

    Optional<E> updateByUserName(SecurityDto securityDto, String userName, T trainerDto);

    boolean changePassword(SecurityDto securityDTO, String username, String newPassword);

    boolean toggleIsActive(SecurityDto securityDTO, String username);

}
