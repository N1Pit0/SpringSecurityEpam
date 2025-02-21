package com.mygym.crm.persistence.daos.traineedao;

import com.mygym.crm.domain.models.Trainee;
import com.mygym.crm.repositories.daorepositories.TraineeDAO;
import com.mygym.crm.persistence.storages.TraineeStorage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("traineeDAOIMPL")
public class TraineeDAOIMPL implements TraineeDAO {

    private TraineeStorage traineeStorage;

    @Autowired
    public void setTraineeStorage(TraineeStorage traineeStorage) {
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

    public TraineeStorage getTraineeStorage() {
        return traineeStorage;
    }
}
