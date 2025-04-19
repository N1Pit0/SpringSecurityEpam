package com.mygym.crm.backstages.interfaces.services;

import java.util.Optional;

public interface CommonUserService<T, E> {

    Optional<E> create(T t);

    Optional<E> update(Long id, T t);

    Optional<E> getByUserName(String userName);

    Optional<E> getById(Long id);

    Optional<E> updateByUserName(String userName, T trainerDto);

    boolean changePassword(String username, String newPassword);

    boolean toggleIsActive(String username);

}
