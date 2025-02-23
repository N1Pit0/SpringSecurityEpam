package com.mygym.crm.backstages.repositories.services;

import java.util.Optional;

public interface BaseService <T, ID>{

    Optional<T> getById(ID id);
}

