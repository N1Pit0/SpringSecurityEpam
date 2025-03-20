package com.mygym.crm.backstages.repositories.services;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;

import java.util.Optional;

public interface UserService <T, E>{

    Optional<E> create(T t);

    Optional<E> update(SecurityDto securityDTO, Long id, T t);

    Optional<E> getByUserName(SecurityDto securityDTO, String userName);

    Optional<E> getById(SecurityDto securityDTO, Long id);

    boolean changePassword(SecurityDto securityDTO, String username, String newPassword);

    boolean toggleIsActive(SecurityDto securityDTO, String username);

}
