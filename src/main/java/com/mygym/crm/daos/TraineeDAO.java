package com.mygym.crm.daos;

import com.mygym.crm.exceptions.NoTrainerException;
import com.mygym.crm.models.Trainee;
import com.mygym.crm.storages.TraineeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TraineeDAO {

    private final TraineeStorage traineeStorage;

    @Autowired
    public TraineeDAO(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    public boolean createTrainee(Trainee trainee) {

        traineeStorage.getStorage().putIfAbsent(trainee.getUserId(), trainee);

       return traineeStorage.getStorage().putIfAbsent(trainee.getUserId(), trainee) != null;
    }

    public boolean updateTrainee(Trainee trainee) {
        return traineeStorage.getStorage().replace(trainee.getUserId(), trainee) != null;
    }

    public boolean deleteTrainee(int traineeId) {
        return traineeStorage.getStorage().remove(traineeId) != null;
    }

    //could have used Optional to redirect null checking to caller. Will try later...
    public Trainee getTrainee(int traineeId) {
        Trainee trainee = traineeStorage.getStorage().get(traineeId);
        if (trainee != null) {
            return trainee;
        }
        throw new NoTrainerException();
    }

}
