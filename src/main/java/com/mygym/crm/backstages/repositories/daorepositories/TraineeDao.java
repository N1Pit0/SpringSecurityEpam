package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TraineeDao extends BaseUserDao<Trainee, Long> {
    Optional<Trainee> delete(Long UserId);

    Optional<Trainee> deleteWithUserName(String username);

    List<Training> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate,
                                       String trainerName, String trainingTypeName);

}
