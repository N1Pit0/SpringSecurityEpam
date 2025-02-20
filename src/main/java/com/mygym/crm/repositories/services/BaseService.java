package com.mygym.crm.repositories.services;

import java.util.Optional;

public interface BaseService <T, ID>{
    void create(T t);

    Optional<T> getById(ID id);
}

