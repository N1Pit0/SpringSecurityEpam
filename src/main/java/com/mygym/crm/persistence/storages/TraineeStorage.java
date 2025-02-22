package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Trainee;
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
public class TraineeStorage implements UserStorage<Integer, Trainee> {
    private final Map<Integer, Trainee> storage = new HashMap<>();

    @Value("${trainee.data.path}")
    private String traineeDataPath;

    @Override
    public Map<Integer, Trainee> getStorage() {
        return storage;
    }

    @PostConstruct
    public void init() {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
            // I will add logging logic later
        }
    }
}

