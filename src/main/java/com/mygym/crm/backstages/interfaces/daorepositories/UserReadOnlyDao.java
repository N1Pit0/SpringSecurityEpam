package com.mygym.crm.backstages.interfaces.daorepositories;

import com.mygym.crm.backstages.domain.models.common.User;

import java.util.Optional;

public interface UserReadOnlyDao {

    Long countSpecificUserName(String specificUserName);

    Optional<User> findByUserName(String userName);
}
