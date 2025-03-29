package com.mygym.crm.backstages.core.services.security;

import com.mygym.crm.backstages.core.dtos.security.SecurityDto;
import com.mygym.crm.backstages.domain.models.common.User;
import com.mygym.crm.backstages.interfaces.daorepositories.UserReadOnlyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
public class  UserSecurityService {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityService.class);
    private UserReadOnlyDao userReadOnlyDao;

    @Autowired
    public void setUserReadOnlyDao(UserReadOnlyDao userReadOnlyDao) {
        this.userReadOnlyDao = userReadOnlyDao;
    }

    @Transactional(readOnly = true)
    public boolean authenticate(SecurityDto securityDto, String username) throws AccessDeniedException {

        if (!securityDto.getUserName().equals(username)) {
            logger.error("UserName {} is not authorized to perform the action", securityDto.getUserName());
            throw new AccessDeniedException("UserName " + securityDto.getUserName() + " is not authorized to perform the action");
        }

        Optional<User> userOptional = userReadOnlyDao.findByUserName(securityDto.getUserName());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean match = user.getPassword().equals(securityDto.getPassword());
            if (match) {
                logger.info("User with username: {} authenticated", securityDto.getUserName());
                return true;
            }
        }
        logger.error("User with username: {} not authenticated", securityDto.getUserName());
        return false;
    }

    @Transactional(readOnly = true)
    public boolean authenticate(SecurityDto securityDto, Long id) throws AccessDeniedException {

        Optional<User> userOptional = userReadOnlyDao.findByUserName(securityDto.getUserName());
        boolean authorizedWithIdMatch = false;
        boolean authenticatedWIthPasswordMatch = false;

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            authorizedWithIdMatch = user.getUserId().equals(id);
            authenticatedWIthPasswordMatch = user.getPassword().equals(securityDto.getPassword());

            if (authorizedWithIdMatch && authenticatedWIthPasswordMatch) {
                logger.info("User with id: {} authenticated", id);
                return true;
            }

            if (!authorizedWithIdMatch) {
                logger.info("User with id: {} not authorized", user.getUserId());
                throw new AccessDeniedException("UserName " + securityDto.getUserName() + " is not authorized to perform the action");
            } else logger.error("User with id: {} not authenticated", user.getUserId());

        }
        return false;
    }
}
