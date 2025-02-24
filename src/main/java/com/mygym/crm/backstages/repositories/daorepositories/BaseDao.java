package com.mygym.crm.backstages.repositories.daorepositories;

import java.util.Optional;

public interface BaseDao<T, ID>{
    Optional<T> create(T model);

    Optional<T> update(T model);

    Optional<T> delete(ID UserId);

    Optional<T> select(ID UserId);

}

