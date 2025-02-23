package com.mygym.crm.backstages.persistence.storages;

import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingKey;
import com.mygym.crm.backstages.domain.models.TrainingTypeEnum;
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
public class TrainingStorage implements UserStorage<TrainingKey, Training> {
    private final Map<TrainingKey, Training> storage = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(TrainingStorage.class);

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
                else{
                    logger.warn("there is not enough info");
                }
            }

        }catch (IOException e){
            logger.error(e.getMessage(), "could not read file");
        }
    }
}
