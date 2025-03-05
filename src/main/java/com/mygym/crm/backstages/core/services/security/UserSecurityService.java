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
    public boolean authenticate(SecurityDTO securityDTO) {
        Optional<User> userOptional = userReadOnlyDao.findByUserName(securityDTO.getUserName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean match = user.getPassword().equals(securityDTO.getPassword());
            if (match){
                logger.info("User " + securityDTO.getUserName() + " authenticated");
                return true;
            }
            else {
                logger.info("User " + securityDTO.getUserName() + " not authenticated");
                return false;
            }
        }
        logger.info("User " + securityDTO.getUserName() + " not authenticated");
        return false;
    }
}
