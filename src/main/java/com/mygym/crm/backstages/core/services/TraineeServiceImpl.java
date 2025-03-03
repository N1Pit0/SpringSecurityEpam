package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.exceptions.NoTraineeException;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import com.mygym.crm.backstages.repositories.services.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService<TraineeDto> {

    private final TraineeDao traineeDAO;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    public TraineeServiceImpl(@Qualifier("traineeDAOIMPL") TraineeDao traineeDAO, UserService userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }

    @Override
    public void create(TraineeDto traineeDTO) {
        Trainee newTrainee = map(traineeDTO);

        logger.info("Trying to generate new password while attempting to create a new trainee");
        newTrainee.setPassword(userService.generatePassword());

        logger.info("Trying to generate new username while attempting to create a new trainee");
        newTrainee.setUserName(userService.generateUserName(traineeDTO));

        logger.info("Trying to create new trainee with ID: {}", newTrainee.getUserId());
        traineeDAO.create(newTrainee);
    }

    @Override
    public void update(Long id, TraineeDto traineeDTO) {
        Trainee oldTrainee = getById(id).orElseThrow(() -> {
            logger.error("Trainee with ID: {} not found", id);
            return new NoTraineeException("could not find trainee with id " + id);
        });
        Trainee newTrainee = map(traineeDTO);

        logger.info("Setting with old UserId Password and UserName");
        newTrainee.setUserId(oldTrainee.getUserId());
        newTrainee.setPassword(oldTrainee.getPassword());
        newTrainee.setUserName(oldTrainee.getUserName());
//        logger.info("new Trainee set with: ID: {} has been set successfully", UserService.uniqueID);

        logger.info("Trying to update Trainee with ID: {}", id);
        traineeDAO.update(newTrainee);
    }

    @Override
    public void delete(Long id) {
        logger.info("Trying to delete Trainee with ID: {}", id);
        traineeDAO.delete(id);
    }

    @Override
    public Optional<Trainee> getById(Long id) {
        logger.info("Trying to find Trainee with ID: {}", id);
        return traineeDAO.select(id);
    }

    private Trainee map(TraineeDto traineeDTO) {
        Trainee trainee = new Trainee();
        logger.info("New Trainee, populating it with given traineeDTO");

        trainee.setFirstName(traineeDTO.getFirstName());
        trainee.setLastName(traineeDTO.getLastName());
        trainee.setIsActive(traineeDTO.isActive());
        trainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        trainee.setAddress(traineeDTO.getAddress());

        logger.info("New Trainee has been successfully populated with given traineeDTO");
        return trainee;
    }
}
