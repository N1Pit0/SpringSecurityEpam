package com.mygym.crm.storages;

import com.mygym.crm.models.Training;
import com.mygym.crm.models.TrainingKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingStorage {
    private final Map<TrainingKey, Training> storage = new HashMap<>();

    public Map<TrainingKey, Training> getStorage() {
        return storage;
    }
}
