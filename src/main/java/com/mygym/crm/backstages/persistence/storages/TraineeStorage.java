package com.mygym.crm.backstages.persistence.storages;

import com.mygym.crm.backstages.domain.models.Trainee;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeStorage implements UserStorage<Integer, Trainee> {
    private Map<Integer, Trainee> storage;
    private static final Logger logger = LoggerFactory.getLogger(TraineeStorage.class);

    @Value("${trainee.data.path}")
    private String traineeDataPath;

    @Override
    public Map<Integer, Trainee> getStorage() {
        return storage;
    }

    @PostConstruct
    public void init() {
        storage = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(traineeDataPath))) {

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                if (parts.length >= 8) {
                    Trainee trainee = new Trainee();

                    int id = Integer.parseInt(parts[0].trim());
                    trainee.setUserId(id);

                    String firstName = parts[1].trim();
                    trainee.setFirstName(firstName);

                    String lastName = parts[2].trim();
                    trainee.setLastName(lastName);

                    String username = parts[3].trim();
                    trainee.setUserName(username);

                    String password = parts[4].trim();
                    trainee.setPassword(password);

                    boolean isActive = Boolean.parseBoolean(parts[5].trim());
                    trainee.setActive(isActive);

                    LocalDate dateOfBirth = LocalDate.parse(parts[6].trim());
                    trainee.setDateOfBirth(dateOfBirth);

                    String address = parts[7].trim();
                    trainee.setAddress(address);

                    storage.put(id, trainee);
                }

                else{
                    logger.warn("there is not enough info");
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), "could not read file");
        }
    }
}

