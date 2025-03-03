package com.mygym.crm.backstages.repositories.daorepositories;

import java.util.List;

public interface UserDaoReadOnly {
    List<String> findAllUserNames();

    Long countSpecificUserName(String specificUserName);
}
