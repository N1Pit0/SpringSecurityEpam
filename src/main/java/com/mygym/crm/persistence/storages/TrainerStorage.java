package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Trainer;
import com.mygym.crm.domain.models.TrainingTypeEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainerStorage implements UserStorage<Integer, Trainer> {
    private final Map<Integer, Trainer> storage = new HashMap<>();

    @Value("${trainer.data.path}")
    private String trainerDataPath;

    @Override
    public Map<Integer, Trainer> getStorage() {
        return storage;
    }

    @PostConstruct
    public void init() {
        try (BufferedReader br = new BufferedReader(new FileReader(trainerDataPath))){

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length >= 7) {
                    Trainer trainer = new Trainer();

                    int id = Integer.parseInt(parts[0].trim());
                    trainer.setUserId(id);

                    String firstName = parts[1].trim();
                    trainer.setFirstName(firstName);

                    String lastName = parts[2].trim();
                    trainer.setLastName(lastName);

                    String username = parts[3].trim();
                    trainer.setUserName(username);

                    String password = parts[4].trim();
                    trainer.setPassword(password);

                    boolean isActive = Boolean.parseBoolean(parts[5].trim());
                    trainer.setActive(isActive);

                    TrainingTypeEnum trainingType = TrainingTypeEnum.valueOf(parts[6].trim());
                    trainer.setTrainingType(trainingType);

                    storage.put(id, trainer);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            // I will add logging logic later
        }
    }
}
