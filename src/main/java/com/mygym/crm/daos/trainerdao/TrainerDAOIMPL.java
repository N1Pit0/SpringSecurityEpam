package com.mygym.crm.daos.trainerdao;

import com.mygym.crm.models.Trainer;
import com.mygym.crm.repositories.daorepositories.TrainerDAO;


public class TrainerDAOIMPL implements TrainerDAO {
    @Override
    public boolean create(Trainer model) {
        return false;
    }

    @Override
    public boolean update(Trainer model) {
        return false;
    }

    @Override
    public boolean delete(Integer UserId) {
        return false;
    }

    @Override
    public Trainer select(Integer UserId) {
        return null;
    }
}
