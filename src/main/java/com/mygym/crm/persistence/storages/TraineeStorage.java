package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Trainee;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeStorage {

    private final Map<Integer, Trainee> storage = new HashMap<>();

    public Map<Integer, Trainee> getStorage() {
        return storage;
    }
}

