package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.common.User;

import java.util.List;
import java.util.Optional;

public interface UserReadOnlyDao {
    List<String> findAllUserNames();

    Long countSpecificUserName(String specificUserName);

    Optional<User> findByUserName(String userName);
}
