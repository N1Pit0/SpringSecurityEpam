package com.mygym.crm.repositories.services;

import java.util.Optional;

public interface BaseService <T, ID>{
    void create(T t);

    void update(T t);

    void delete(ID id);

    Optional<T> getById(ID id);
}
