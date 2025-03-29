package com.mygym.crm.backstages.interfaces.daorepositories;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface TraineeDao extends BaseUserDao<Trainee, Long> {
    Optional<Trainee> delete(Long UserId);

    Optional<Trainee> deleteWithUserName(String username);

    Set<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate,
                                      String trainerName, String trainingTypeName);


    Optional<Trainee> updateByUserName(Trainee trainee);

    Set<Trainer> getTrainersNotTrainingTraineesWithUserName(String userName);

}
