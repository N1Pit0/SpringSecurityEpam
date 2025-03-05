package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;

import java.util.Optional;

public interface UserService <T, E>{

    void create(T t);

    void update(SecurityDTO securityDTO, Long id, T t);

    Optional<E> getByUserName(SecurityDTO securityDTO, String userName);

    Optional<E> getById(SecurityDTO securityDTO, Long id);

}
