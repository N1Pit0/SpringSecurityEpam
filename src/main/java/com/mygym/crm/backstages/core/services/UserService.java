package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.common.UserDto;
import com.mygym.crm.backstages.repositories.daorepositories.UserReadOnlyDao;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.Random;
import java.util.Set;

@Service
public class UserService{

    private UserReadOnlyDao userDao;
    private Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public void setUserDao(UserReadOnlyDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> void validateDto(T Dto){
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(Dto);
        if (!constraintViolations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : constraintViolations) {
                sb.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }

            logger.error(sb.toString());
            throw new IllegalArgumentException("Validation errors: " + sb.toString());
        }
    }

    public String generateUserName(UserDto userDto){
        String baseUserName = userDto.getFirstName() + "." + userDto.getLastName();

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
