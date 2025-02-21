package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Training;
import com.mygym.crm.domain.models.TrainingKey;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainingStorage implements UserStorage<TrainingKey, Training> {
    private final Map<TrainingKey, Training> storage = new HashMap<>();

    @Override
    public Map<TrainingKey, Training> getStorage() {
        return storage;
    }
}
