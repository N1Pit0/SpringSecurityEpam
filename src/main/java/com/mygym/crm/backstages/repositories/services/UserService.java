package com.mygym.crm.backstages.repositories.services;

import java.util.Optional;

public interface UserService <T, E>{

    void create(T t);

    void update(Long id, T t);

    Optional<E> getByUserName(String userName);
}
