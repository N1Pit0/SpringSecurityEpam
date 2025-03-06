package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
import com.mygym.crm.backstages.domain.models.common.User;
import com.mygym.crm.backstages.persistence.daos.userdaoreadonly.UserReadOnlyDaoImpl;
import com.mygym.crm.backstages.repositories.daorepositories.UserReadOnlyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserSecurityService {

    private UserReadOnlyDao userReadOnlyDao;
    private static final Logger logger = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    public void setUserReadOnlyDao(UserReadOnlyDao userReadOnlyDao) {
        this.userReadOnlyDao = userReadOnlyDao;
    }

    @Transactional(readOnly = true)
    public boolean authenticate(SecurityDTO securityDTO, String username) {
        if (!securityDTO.getUserName().equals(username)) {
            logger.error("UserName {} is not authorized to perform the action", securityDTO.getUserName());
            return false;
        }

        Optional<User> userOptional = userReadOnlyDao.findByUserName(securityDTO.getUserName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean match = user.getPassword().equals(securityDTO.getPassword());
            if (match){
                logger.info("User with username: {} authenticated", securityDTO.getUserName());
                return true;
            }
        }
        logger.error("User with username: {} not authenticated", securityDTO.getUserName());
        return false;
    }

    @Transactional(readOnly = true)
    public boolean authenticate(SecurityDTO securityDTO, Long id) {

        Optional<User> userOptional = userReadOnlyDao.findByUserName(securityDTO.getUserName());
        boolean authorizedWithIdMatch = false;
        boolean authenticatedWIthPasswordMatch = false;

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            authorizedWithIdMatch = user.getUserId().equals(id);
            authenticatedWIthPasswordMatch = user.getPassword().equals(securityDTO.getPassword());

            if (authorizedWithIdMatch && authenticatedWIthPasswordMatch){
                logger.info("User with id: {} authenticated", id);
                return true;
            }

            if(!authorizedWithIdMatch){
                logger.info("User with id: {} not authorized", user.getUserId());
            }
            else logger.error("User with id: {} not authenticated", user.getUserId());

        }
        return false;
    }
}
