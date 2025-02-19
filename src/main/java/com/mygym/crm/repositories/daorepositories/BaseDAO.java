package com.mygym.crm.repositories.daorepositories;

public interface BaseDAO <T, ID>{
    boolean create(T model);

    boolean update(T model);

    boolean delete(ID UserId);

    T select(ID UserId);

}
