package com.mygym.crm.daos.traineedao;

import com.mygym.crm.exceptions.NoTraineeException;
import com.mygym.crm.models.Trainee;
import com.mygym.crm.repositories.daorepositories.TraineeDAO;
import com.mygym.crm.storages.TraineeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public class TraineeDAOIMPL implements TraineeDAO {

    private final TraineeStorage traineeStorage;

    @Autowired
    public TraineeDAOIMPL(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public Optional<Trainee> create(Trainee trainee) {
        Trainee previous =  traineeStorage.getStorage()
                .putIfAbsent(trainee.getUserId(),trainee);

        return (previous == null)? Optional.of(trainee) //Successfully added
            : Optional.empty();// Trainee already exists.
    }

    @Override
    public Optional<Trainee> update(Trainee trainee) {
        Trainee previous = traineeStorage.getStorage().replace(trainee.getUserId(), trainee);

        return (previous != null)? Optional.of(trainee) //Successfully added
                : Optional.empty();// Trainee already exists.
    }

    @Override
    public Optional<Trainee> delete(Integer traineeId) {
        Trainee removedTrainer  = traineeStorage.getStorage().remove(traineeId);

        return Optional.ofNullable(removedTrainer); // Returns Optional.empty() if not present.
    }

    @Override
    public Optional<Trainee> select(Integer traineeId) {
        Trainee trainee = traineeStorage.getStorage().get(traineeId);

        return Optional.ofNullable(trainee); // Returns Optional.empty() if not present.
    }

}
