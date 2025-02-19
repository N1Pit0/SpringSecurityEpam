package com.mygym.crm.repositories.daorepositories;

import java.util.Optional;

public interface BaseDAO <T, ID>{
    Optional<T> create(T model);

    Optional<T> update(T model);

    Optional<T> delete(ID UserId);

    Optional<T> select(ID UserId);

}

