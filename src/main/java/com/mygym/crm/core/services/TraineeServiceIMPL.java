package com.mygym.crm.core.services;

import com.mygym.crm.core.services.dtos.TraineeDTO;
import com.mygym.crm.domain.models.Trainee;
import com.mygym.crm.exceptions.NoTraineeException;
import com.mygym.crm.repositories.daorepositories.TraineeDAO;
import com.mygym.crm.repositories.services.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeServiceIMPL implements TraineeService<TraineeDTO> {

    private final TraineeDAO traineeDAO;
    private final UserService<Integer, Trainee> userService;

    @Autowired
    public TraineeServiceIMPL(@Qualifier("traineeServiceIMPL") TraineeDAO traineeDAO, UserService<Integer, Trainee> userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }

    @Override
    public void create(TraineeDTO traineeDTO) {
        Trainee newTrainee = map(traineeDTO);
        newTrainee.setUserId(1);//ID generation is not implemented yet
        newTrainee.setPassword(userService.generatePassword());

        traineeDAO.create(newTrainee);
    }

    @Override
    public void update(Integer id,TraineeDTO traineeDTO) {
        Trainee oldTrainee = getById(id).orElseThrow(() -> new NoTraineeException("could not find trainee with id " + id));
        Trainee newTrainee = map(traineeDTO);

        newTrainee.setUserId(oldTrainee.getUserId());
        newTrainee.setPassword(oldTrainee.getPassword());
        newTrainee.setUserName(oldTrainee.getUserName());

        traineeDAO.update(newTrainee);
    }

    @Override
    public void delete(Integer id) {
        traineeDAO.delete(id);
    }

    @Override
    public Optional<Trainee> getById(Integer id) {
        return traineeDAO.select(id);
    }

    private Trainee map(TraineeDTO traineeDTO) {
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeDTO.getFirstName());
        trainee.setLastName(traineeDTO.getLastName());
        trainee.setActive(traineeDTO.isActive());
        trainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        trainee.setAddress(traineeDTO.getAddress());

        return trainee;
    }
}
