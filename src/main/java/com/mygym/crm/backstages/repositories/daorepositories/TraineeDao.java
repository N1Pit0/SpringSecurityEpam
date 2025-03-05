package com.mygym.crm.backstages.repositories.daorepositories;

import com.mygym.crm.backstages.domain.models.Trainee;

import java.util.Optional;

public interface TraineeDao extends BaseDao<Trainee, Long> {
    Optional<Trainee> delete(Long UserId);

    Optional<Trainee> deleteWithUserName(String username);

    Optional<Trainee> selectWithUserName(String username);
}
