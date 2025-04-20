package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.request.traineedto.TraineeDto;
import com.mygym.crm.backstages.domain.models.Authorities;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.NoTraineeException;
import com.mygym.crm.backstages.interfaces.daorepositories.TraineeDao;
import com.mygym.crm.backstages.interfaces.services.AuthoritiesService;
import com.mygym.crm.backstages.interfaces.services.TraineeServiceCommon;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TraineeServiceImplCommon implements TraineeServiceCommon {

    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImplCommon.class);
    private final TraineeDao traineeDao;
    private final UserService userService;
    private final AuthoritiesService authoritiesService;

    @Autowired
    public TraineeServiceImplCommon(@Qualifier("traineeDaoImpl") TraineeDao traineeDao, UserService userService, AuthoritiesService authoritiesService) {
        this.traineeDao = traineeDao;
        this.userService = userService;
        this.authoritiesService = authoritiesService;
    }

    @Transactional
    @Override
    public Optional<Trainee> create(TraineeDto traineeDto) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            Trainee newTrainee = map(traineeDto);
            newTrainee.setIsActive(true);

            logger.info("Trying to generate new password while attempting to create a new trainee");
            newTrainee.setPassword(userService.generatePassword());

            logger.info("Trying to generate new username while attempting to create a new trainee");
            newTrainee.setUserName(userService.generateUserName(traineeDto));

            logger.info("Trying to create new trainee with UserName: {}", newTrainee.getUserName());
            Optional<Trainee> optionalTrainee = traineeDao.create(newTrainee);

            optionalTrainee.ifPresentOrElse(
                    (trainee) -> {

                        Authorities userauthorities = new Authorities();
                        userauthorities.setAuthority("ROLE_USER");
                        userauthorities.setUser(newTrainee);
                        authoritiesService.createAuthority(userauthorities);

                        logger.info("trainee with userName: {} has been created", trainee.getUserName());
                    },
                    () -> logger.warn("trainee with userName: {} was not created", newTrainee.getUserName())
            );

            return optionalTrainee;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> update(Long id, TraineeDto traineeDto) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            Trainee oldTrainee = getById(id).orElseThrow(() -> {
                logger.error("Trainee with ID: {} not found", id);
                return new NoTraineeException("could not find trainee with id " + id);
            });
            Trainee newTrainee = map(traineeDto);

            logger.info("Setting with old UserId Password and UserName");
            newTrainee.setUserId(oldTrainee.getUserId());
            newTrainee.setPassword(oldTrainee.getPassword());
            newTrainee.setUserName(oldTrainee.getUserName());
            newTrainee.setTrainings(oldTrainee.getTrainings());
            newTrainee.setIsActive(oldTrainee.getIsActive());
            newTrainee.setAuthorities(oldTrainee.getAuthorities());

            logger.info("Trying to update Trainee with ID: {}", id);
            Optional<Trainee> optionalTrainee = traineeDao.update(newTrainee);

            optionalTrainee.ifPresentOrElse(
                    (trainee) -> {
                        logger.info("trainee with ID: {} has been updated", trainee.getUserId());
                        trainee.getTrainings().size();
                    },
                    () -> logger.warn("trainee with ID: {} was not updated", newTrainee.getUserId())
            );

            return optionalTrainee;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> updateByUserName(String userName, TraineeDto traineeDto) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            Trainee oldTrainee = getByUserName(userName).orElseThrow(() -> {
                logger.error("Trainee with UserName {} not found", userName);
                return new NoTraineeException("could not find trainee with UserName " + userName);
            });
            Trainee newTrainee = map(traineeDto);

            logger.info("Setting with old UserId Password and UserName inside updateByUserName");
            newTrainee.setUserId(oldTrainee.getUserId());
            newTrainee.setPassword(oldTrainee.getPassword());
            newTrainee.setUserName(oldTrainee.getUserName());
            newTrainee.setTrainings(oldTrainee.getTrainings());
            newTrainee.setIsActive(oldTrainee.getIsActive());
            newTrainee.setAuthorities(oldTrainee.getAuthorities());

            logger.info("Trying to update Trainee with userName: {}", userName);
            Optional<Trainee> optionalTrainee = traineeDao.update(newTrainee);

            optionalTrainee.ifPresentOrElse(
                    (trainee) -> {
                        logger.info("trainee with userName: {} has been updated", trainee.getUserName());
                        trainee.getTrainings().size();
                    },
                    () -> logger.warn("trainee with userName: {} was not updated", newTrainee.getUserName())
            );

            return optionalTrainee;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> delete(Long id) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("Trying to delete Trainee with ID: {}", id);
            Optional<Trainee> optionalTrainee = traineeDao.delete(id);

            optionalTrainee.ifPresentOrElse(
                    (trainee) -> logger.info("trainee with userId: {} has been deleted", trainee.getUserId()),
                    () -> logger.warn("trainee with userId: {} was not deleted", id)
            );

            return optionalTrainee;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional
    @Override
    public Optional<Trainee> deleteWithUserName(String userName) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("Trying to delete Trainee with userName: {}", userName);
            Optional<Trainee> optionalTrainee = traineeDao.deleteWithUserName(userName);

            optionalTrainee.ifPresentOrElse(
                    (trainee) -> logger.info("trainee with userName: {} has been deleted", trainee.getUserName()),
                    () -> logger.warn("trainee with userName: {} can't be found", userName)
            );

            return optionalTrainee;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)

    @Override
    public Optional<Trainee> getById(Long id) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("Trying to find Trainee with ID: {}", id);

            Optional<Trainee> traineeOptional = traineeDao.select(id);

            traineeOptional.ifPresentOrElse(
                    trainee -> {
                        trainee.getTrainings().size();
                        logger.info("Found Trainee with ID: {}", id);
                    },
                    () -> logger.warn("No Trainee found with ID: {}", id)
            );

            return traineeOptional;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @Override
    public Optional<Trainee> getByUserName(String userName) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("Trying to find Trainee with UserName: {}", userName);

            Optional<Trainee> traineeOptional = traineeDao.selectWithUserName(userName);

            traineeOptional.ifPresentOrElse(
                    trainee -> logger.info("Found Trainee with UserName: {}", userName),
                    () -> logger.warn("No Trainee found with UserName: {}", userName)
            );

            return traineeOptional;
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional
    @Override
    public boolean changePassword(String username, String newPassword) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("Trying to change password for Trainee with UserName: {}", username);

            boolean success = traineeDao.changePassword(username, userService.encodePassword(newPassword));

            if (success) {
                logger.info("Successfully changed password for Trainee with UserName: {}", username);
                return true;
            } else {
                logger.warn("Failed to change password for Trainee with UserName: {}", username);
                return false;
            }
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional
    @Override
    public boolean toggleIsActive(String username) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            logger.info("Trying to toggle isActive for Trainee with UserName: {}", username);

            boolean success = traineeDao.toggleIsActive(username);

            if (success) {
                logger.info("Successfully toggled isActive for Trainee with UserName: {}", username);
                return true;
            } else {
                logger.warn("Failed to toggled isActive for Trainee with UserName: {}", username);
                return false;
            }
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @Override
    public Optional<Set<Training>> getTraineeTrainings(String username, LocalDate fromDate,
                                                       LocalDate toDate, String trainerName, String trainingTypeName) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            Set<Training> trainings = traineeDao.getTraineeTrainings(username, fromDate, toDate, trainerName, trainingTypeName);
            if (trainings.isEmpty()) {
                logger.warn("No training found for Trainee with UserName: {}", username);
            } else
                logger.info("training record of size: {} was found for Trainee with UserName: {}", trainings.size(), username);
            return Optional.of(trainings);
        } finally {
            MDC.remove("transactionId");
        }
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @Override
    public Optional<Set<Trainer>> getTrainersNotTrainingTraineesWithUserName(String traineeUserName) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);

        try {
            Set<Trainer> trainers = traineeDao.getTrainersNotTrainingTraineesWithUserName(traineeUserName);
            if (trainers.isEmpty()) {
                logger.warn("No unassigned trainers found");
            } else
                logger.info("Trainer record of size: {} was found for Trainers not matched with Trainee with username: {}",
                        trainers.size(), traineeUserName);
            return Optional.of(trainers);
        } finally {
            MDC.remove("transactionId");
        }
    }

    private Trainee map(TraineeDto traineeDto) {
        Trainee trainee = new Trainee();
        logger.info("New Trainee, populating it with given traineeDto");

        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setAddress(traineeDto.getAddress());

        logger.info("New Trainee has been successfully populated with given traineeDto");
        return trainee;
    }
}
