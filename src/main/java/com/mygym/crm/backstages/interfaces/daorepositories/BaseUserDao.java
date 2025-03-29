package com.mygym.crm.backstages.interfaces.daorepositories;

import java.util.Optional;

public interface BaseUserDao<T, ID> {
    Optional<T> create(T model);

    Optional<T> update(T model);

    Optional<T> select(ID UserId);

    Optional<T> selectWithUserName(String username);

    boolean changePassword(String username, String newPassword);

    boolean toggleIsActive(String username);

}

