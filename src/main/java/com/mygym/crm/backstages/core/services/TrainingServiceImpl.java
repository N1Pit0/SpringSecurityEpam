package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.domain.models.TrainingType;
import com.mygym.crm.backstages.repositories.daorepositories.TraineeDao;
import com.mygym.crm.backstages.repositories.daorepositories.TrainerDao;
import com.mygym.crm.backstages.repositories.daorepositories.TrainingDao;
import com.mygym.crm.backstages.repositories.services.TrainingService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService{

    private final TrainingDao trainingDAO;
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);


    @Autowired
    public TrainingServiceImpl(TrainingDao trainingDAO, TrainerDao trainerDao, TraineeDao traineeDao) {
        this.trainingDAO = trainingDAO;
        this.trainerDao = trainerDao;
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @Override
    public Optional<Training> add(TrainingDto trainingDto) {
        userService.validateDto(trainingDto);

        Training newTraining = new Training();
        logger.info("New Training, populating it with given traineeDTO");

        newTraining.setTrainingName(trainingDto.getTrainingName());
        newTraining.setTrainingDate(trainingDto.getTrainingDate());
        newTraining.setTrainingDuration(trainingDto.getTrainingDuration());

        Trainer trainer = trainerDao.select(trainingDto.getTrainerId()).orElse(null);
        Trainee trainee = traineeDao.select(trainingDto.getTraineeId()).orElse(null);
        assert trainer != null;
        TrainingType trainingType = trainer.getTrainingType();

        newTraining.setTrainer(trainer);
        newTraining.setTrainee(trainee);
        newTraining.setTrainingType(trainingType);

        logger.info("Trying to new create training");
        Optional<Training> optionalTraining = trainingDAO.add(newTraining);

        optionalTraining.ifPresentOrElse(
                (training) -> logger.info("Training with trainingId: {} has been created", training.getId()),
                () -> logger.warn("Training could not be created")
        );

        return optionalTraining;
    }

    @Transactional(noRollbackFor = HibernateException.class, readOnly = true)
    @Override
    public Optional<Training> getById(Long id) {
        logger.info("Trying to find Training with ID: {}", id);

        Optional<Training> trainingOptional = trainingDAO.select(id);

        trainingOptional.ifPresentOrElse(
                training -> {
                    training.getTrainee().getUserId();
                    training.getTrainer().getUserId();
                    training.getTrainingType().getTrainingTypeId();
                    logger.info("Found Training with ID: {}", id);
                },
                () -> logger.warn("No training found with ID: {}", id)
        );

        return trainingOptional;
    }

}
