package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.common.UserDto;
import com.mygym.crm.backstages.repositories.daorepositories.UserReadOnlyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService{

    private UserReadOnlyDao userDao;

    @Autowired
    public void setUserDao(UserReadOnlyDao userDao) {
        this.userDao = userDao;
    }

    public String generateUserName(UserDto userDTO){
        String baseUserName = userDTO.getFirstName() + "." + userDTO.getLastName();

        Long userNameCount = userDao.countSpecificUserName(baseUserName);

        return userNameCount == 0 ? baseUserName : baseUserName + userNameCount;
    }

    public String generatePassword(){
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            char c = (char) (random.nextInt(33,127)); //ASCII representation of symbols for password
            password.append(c);
        }

        return password.toString();
    }
}
