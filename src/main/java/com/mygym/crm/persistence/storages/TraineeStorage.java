package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Trainee;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeStorage implements UserStorage<Integer, Trainee> {

    private final Map<Integer, Trainee> storage = new HashMap<>();

    @Override
    public Map<Integer, Trainee> getStorage() {
        return storage;
    }
}

