package com.mygym.crm.repositories.services;

import java.util.Optional;

public interface BaseService <T, ID>{

    Optional<T> getById(ID id);
}

