package com.mygym.crm.backstages.persistence.storages;

import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.TrainingType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TrainerStorage implements UserStorage<Integer, Trainer> {
    private  Map<Integer, Trainer> storage;

    @Value("${trainer.data.path}")
    private String trainerDataPath;

    @Override
    public Map<Integer, Trainer> getStorage() {
        return storage;
    }
    private static final Logger logger = LoggerFactory.getLogger(TrainerStorage.class);

    @PostConstruct
    public void init() {
        storage = new HashMap<>();
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
                    trainer.setIsActive(isActive);


                    storage.put(id, trainer);
                }
                else{
                    logger.warn("there is not enough info");
                }
            }
        }catch (IOException e){
            logger.error(e.getMessage(), "could not read file");
        }
    }
}
