package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Trainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainerStorage {
    private final Map<Integer, Trainer> storage = new HashMap<>();

    public Map<Integer, Trainer> getStorage() {
        return storage;
    }
}
