package com.mygym.crm.persistence.storages;

import com.mygym.crm.domain.models.Training;
import com.mygym.crm.domain.models.TrainingKey;
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
public class TrainingStorage implements UserStorage<TrainingKey, Training> {
    private final Map<TrainingKey, Training> storage = new HashMap<>();

    @Value("${training.data.path}")
    private String trainingDataPath;

    @Override
    public Map<TrainingKey, Training> getStorage() {
        return storage;
    }

    @PostConstruct
    public void init(){
        try(BufferedReader br = new BufferedReader(new FileReader(trainingDataPath))) {

            String line;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] parts = line.split(";");

                if(parts.length >= 6){
                    Training training = new Training();

                    Integer traineeId = Integer.parseInt(parts[0].trim());
                    Integer trainerId = Integer.parseInt(parts[1].trim());
                    TrainingKey key = new TrainingKey(traineeId, trainerId);
                    training.setTrainingKey(key);

                    String traineeName = parts[2].trim();
                    training.setTrainingName(traineeName);

                    TrainingTypeEnum trainingType = TrainingTypeEnum.valueOf(parts[3].trim());
                    training.setTrainingType(trainingType);

                    LocalDate date = LocalDate.parse(parts[4].trim());
                    training.setTrainingDate(date);

                    int trainingDuration = Integer.parseInt(parts[5].trim());
                    training.setTrainingDuration(trainingDuration);

                    storage.put(key, training);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
            //I will add logging later
        }
    }
}
