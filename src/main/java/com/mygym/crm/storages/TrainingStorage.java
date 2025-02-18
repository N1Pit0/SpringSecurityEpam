package com.mygym.crm.storages;

import com.mygym.crm.models.Trainee;
import com.mygym.crm.models.Training;

import java.util.HashMap;
import java.util.Map;

public class TrainingStorage {
    private final Map<Integer, Training> storage = new HashMap<>();

    public Map<Integer, Training> getStorage() {
        return storage;
    }
}
