package com.mygym.crm.daos.traineedao;

import com.mygym.crm.exceptions.NoTraineeException;
import com.mygym.crm.models.Trainee;
import com.mygym.crm.repositories.daorepositories.TraineeDAO;
import com.mygym.crm.storages.TraineeStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class TraineeDAOIMPL implements TraineeDAO {

    private final TraineeStorage traineeStorage;

    @Autowired
    public TraineeDAOIMPL(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public boolean create(Trainee trainee) {

        System.out.println("TraineeDAOIMPL.create");
        return traineeStorage.getStorage().putIfAbsent(trainee.getUserId(), trainee) != null;
    }

    @Override
    public boolean update(Trainee trainee) {
        return traineeStorage.getStorage().replace(trainee.getUserId(), trainee) != null;
    }

    @Override
    public boolean delete(Integer traineeId) {
        return traineeStorage.getStorage().remove(traineeId) != null;
    }

    @Override
    //could have used Optional to redirect null checking to caller. Will try later...
    public Trainee select(Integer traineeId) {
        Trainee trainee = traineeStorage.getStorage().get(traineeId);
        if (trainee != null) {
            return trainee;
        }
        throw new NoTraineeException("Your trainee is not in the base");
    }

}
