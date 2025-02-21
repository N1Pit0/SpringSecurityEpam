package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Trainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainerStorage implements UserStorage<Integer, Trainer> {
    private final Map<Integer, Trainer> storage = new HashMap<>();

    @Override
    public Map<Integer, Trainer> getStorage() {
        return storage;
    }
}
