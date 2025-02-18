package com.mygym.crm.storages;

import com.mygym.crm.models.Trainee;
import com.mygym.crm.models.Trainer;

import java.util.HashMap;
import java.util.Map;

public class TrainerStorage {
    private final Map<Integer, Trainer> storage = new HashMap<>();

    public Map<Integer, Trainer> getStorage() {
        return storage;
    }
}
