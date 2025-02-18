package com.mygym.crm.storages;

import com.mygym.crm.models.Trainee;

import java.util.HashMap;
import java.util.Map;

public class TrainerStorage {
    private final Map<Integer, Trainee> storage = new HashMap<>();

    public Map<Integer, Trainee> getStorage() {
        return storage;
    }
}
