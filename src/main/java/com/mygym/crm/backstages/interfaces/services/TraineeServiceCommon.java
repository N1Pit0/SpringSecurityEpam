package com.mygym.crm.backstages.interfaces.services;


import com.mygym.crm.backstages.core.dtos.request.traineedto.TraineeDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface TraineeServiceCommon extends CommonUserService<TraineeDto, Trainee> {

    Optional<Trainee> delete(Long id);

    Optional<Trainee> deleteWithUserName(String userName);

    Optional<Set<Training>> getTraineeTrainings(String username, LocalDate fromDate,
                                                LocalDate toDate, String trainerName, String trainingTypeName);

    Optional<Set<Trainer>> getTrainersNotTrainingTraineesWithUserName(String TraineeUserName);

}
