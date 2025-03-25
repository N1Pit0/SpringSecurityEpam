package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TrainerDao extends BaseUserDao<Trainer, Long> {

    Set<Training> getTrainerTrainings(String username, LocalDate fromDate, LocalDate toDate,
                                      String traineeName);

    List<Trainer> getTrainersNotTrainingTraineesWithUserName(String userName);
}
