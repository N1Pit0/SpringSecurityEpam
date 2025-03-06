package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.Annotations.SecutiryAnnotations.SecureMethod;
import com.mygym.crm.backstages.core.dtos.TraineeDto;
import com.mygym.crm.backstages.core.dtos.security.SecurityDTO;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.exceptions.NoTraineeException;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import com.mygym.crm.backstages.repositories.services.TraineeService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TraineeServiceImpl implements TraineeService{

    private final TraineeDao traineeDAO;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    public TraineeServiceImpl(@Qualifier("traineeDAOIMPL") TraineeDao traineeDAO, UserService userService) {
        this.traineeDAO = traineeDAO;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void create(TraineeDto traineeDTO) {
        Trainee newTrainee = map(traineeDTO);

        logger.info("Trying to generate new password while attempting to create a new trainee");
        newTrainee.setPassword(userService.generatePassword());

        logger.info("Trying to generate new username while attempting to create a new trainee");
        newTrainee.setUserName(userService.generateUserName(traineeDTO));

        logger.info("Trying to create new trainee with UserName: {}", newTrainee.getUserName());
        traineeDAO.create(newTrainee).ifPresentOrElse(
                (trainee) -> logger.info("trainee with userName: {} has been created", trainee.getUserName()),
                () -> logger.warn("trainee with userName: {} was not created", newTrainee.getUserName())
        );
    }

    @Transactional
    @SecureMethod
    @Override
    public void update(SecurityDTO securityDTO,Long id, TraineeDto traineeDTO) {
        Trainee oldTrainee = getById(securityDTO, id).orElseThrow(() -> {
            logger.error("Trainee with ID: {} not found", id);
            return new NoTraineeException("could not find trainee with id " + id);
        });
        Trainee newTrainee = map(traineeDTO);

        logger.info("Setting with old UserId Password and UserName");
        newTrainee.setUserId(oldTrainee.getUserId());
        newTrainee.setPassword(oldTrainee.getPassword());
        newTrainee.setUserName(oldTrainee.getUserName());

        logger.info("Trying to update Trainee with ID: {}", id);
        traineeDAO.update(newTrainee);
    }

    @Transactional
    @SecureMethod
    @Override
    public void delete(SecurityDTO securityDTO, Long id) {
        logger.info("Trying to delete Trainee with ID: {}", id);
        traineeDAO.delete(id);
    }

    @Transactional
    @SecureMethod
    @Override
    public void deleteWithUserName(SecurityDTO securityDTO,String userName) {
        logger.info("Trying to delete Trainee with userName: {}", userName);
        traineeDAO.deleteWithUserName(userName)
                .ifPresentOrElse(
                        (trainee) -> logger.info("trainee with userName: {} has been deleted", trainee.getUserName()),
                        () -> logger.warn("trainee with userName: {} can't be found", userName)
                );
    }

    @Transactional(noRollbackFor= HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Trainee> getById(SecurityDTO securityDTO, Long id) {
        logger.info("Trying to find Trainee with ID: {}", id);

        Optional<Trainee> traineeOptional = traineeDAO.select(id);

        Trainee trainee = traineeOptional.orElseThrow(() -> new NoTraineeException("not trainee"));
        trainee.getTrainings().size();

        logger.info("Found Trainee with ID: {}", id);

        return traineeOptional;
    }

    @Transactional(noRollbackFor= HibernateException.class, readOnly = true)
    @SecureMethod
    @Override
    public Optional<Trainee> getByUserName(SecurityDTO securityDTO, String userName){
        logger.info("Trying to find Trainee with UserName: {}", userName);

        Optional<Trainee> traineeOptional = traineeDAO.selectWithUserName(userName);

        traineeOptional.ifPresentOrElse(
                trainee -> {
                    trainee.getTrainings().size();
                    logger.info("Found Trainee with UserName: {}", userName);
                },
                () -> logger.warn("No Trainee found with UserName: {}", userName)
        );
        return traineeOptional;
    }

    @Transactional
    @SecureMethod
    @Override
    public boolean changePassword(SecurityDTO securityDTO, String username, String newPassword) {
        logger.info("Trying to change password for Trainee with UserName: {}", username);

        boolean success = traineeDAO.changePassword(username, newPassword);

        if(success){
            logger.info("Successfully changed password for Trainee with UserName: {}", username);
            return true;
        }
        else {
            logger.warn("Failed to change password for Trainee with UserName: {}", username);
            return false;
        }
    }

    @Transactional
    @SecureMethod
    @Override
    public boolean toggleIsActive(SecurityDTO securityDTO, String username) {
        logger.info("Trying to toggle isActive for Trainee with UserName: {}", username);

        boolean success = traineeDAO.toggleIsActive(username);

        if(success){
            logger.info("Successfully toggled isActive for Trainee with UserName: {}", username);
            return true;
        }
        else {
            logger.warn("Failed to toggled isActive for Trainee with UserName: {}", username);
            return false;
        }
    }

    private Trainee map(TraineeDto traineeDTO) {
        Trainee trainee = new Trainee();
        logger.info("New Trainee, populating it with given traineeDTO");

        trainee.setFirstName(traineeDTO.getFirstName());
        trainee.setLastName(traineeDTO.getLastName());
        trainee.setDateOfBirth(traineeDTO.getDateOfBirth());
        trainee.setAddress(traineeDTO.getAddress());

        logger.info("New Trainee has been successfully populated with given traineeDTO");
        return trainee;
    }
}
