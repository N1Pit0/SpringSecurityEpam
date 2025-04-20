package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.domain.models.Authorities;
import com.mygym.crm.backstages.repositories.daos.AuthoritiesDao;
import com.mygym.crm.backstages.interfaces.services.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService{

    private final AuthoritiesDao authoritiesDao;

    @Autowired
    public AuthoritiesServiceImpl(AuthoritiesDao authoritiesDao) {
        this.authoritiesDao = authoritiesDao;
    }

    @Override
    public void createAuthority(Authorities authorities) {
        authoritiesDao.saveAndFlush(authorities);
    }
}
