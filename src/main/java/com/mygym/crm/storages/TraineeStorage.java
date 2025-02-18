package com.mygym.crm.storages;

import com.mygym.crm.models.Trainee;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeStorage {

    private final Map<Integer, Trainee> storage = new HashMap<>();

    @Bean
    public Map<Integer, Trainee> getStorage() {
        return storage;
    }
}

