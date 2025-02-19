package com.mygym.crm.daos.trainingdao;

import com.mygym.crm.models.Training;
import com.mygym.crm.models.TrainingKey;
import com.mygym.crm.repositories.daorepositories.TrainingDAO;
import com.mygym.crm.storages.TrainingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TrainingDAOIMPL implements TrainingDAO {

    private final TrainingStorage trainingStorage;

    @Autowired
    public TrainingDAOIMPL(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public Optional<Training> create(Training training) {
        Training previous =  trainingStorage.getStorage()
                .putIfAbsent(training.getTrainingKey(),training);
        System.out.println("inside create");
        return (previous == null)? Optional.of(training) //Successfully added
                : Optional.empty();// Trainee already exists.
    }

    @Override
    public Optional<Training> update(Training training) {
        Training previous = trainingStorage.getStorage().replace(training.getTrainingKey(), training);

        return (previous != null)? Optional.of(training) //Successfully added
                : Optional.empty();// Trainee already exists.
    }

    @Override
    public Optional<Training> delete(TrainingKey trainingKey) {
        Training removedTraining = trainingStorage.getStorage().remove(trainingKey);

        return Optional.ofNullable(removedTraining); // Returns Optional.empty() if not present.
    }

    @Override
    public Optional<Training> select(TrainingKey trainingKey) {
        Training training = trainingStorage.getStorage().get(trainingKey);

        return Optional.ofNullable(training); // Returns Optional.empty() if not present.
    }
}
