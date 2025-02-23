package com.mygym.crm;

import com.mygym.crm.backstages.ApplicationFacade;
import com.mygym.crm.backstages.config.Configs;
import com.mygym.crm.backstages.core.dtos.TraineeDTO;
import com.mygym.crm.backstages.core.dtos.TrainerDTO;
import com.mygym.crm.backstages.core.dtos.TrainingDTO;
import com.mygym.crm.backstages.domain.models.TrainingKey;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configs.class);
        ApplicationFacade facade = context.getBean(ApplicationFacade.class);

        TraineeDTO traineeDTO = new TraineeDTO();

        traineeDTO.setFirstName("John");
        traineeDTO.setLastName("Doe");
        traineeDTO.setActive(true);

        TraineeDTO traineeDTO1 = new TraineeDTO();

        traineeDTO1.setFirstName("John");
        traineeDTO1.setLastName("Doe");
        traineeDTO1.setActive(true);

        TrainerDTO trainerDTO = new TrainerDTO();

        trainerDTO.setFirstName("John");
        trainerDTO.setLastName("Doe");
        trainerDTO.setActive(true);

        TrainerDTO trainerDTO1 = new TrainerDTO();

        trainerDTO1.setFirstName("sad");
        trainerDTO1.setLastName("sadasd");
        trainerDTO1.setActive(true);

        TrainingKey trainingKey = new TrainingKey();
        trainingKey.setTraineeId(6);
        trainingKey.setTrainerId(18);

        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingKey(trainingKey);
        trainingDTO.setTrainingDuration(2);
        trainingDTO.setTrainingName("adasdsad");

        System.out.println(facade.selectTrainee(6));
        facade.createTrainee(traineeDTO);
        facade.updateTrainee(6, traineeDTO1);
        facade.deleteTrainee(6);

        System.out.println(facade.selectTrainer(18));
        facade.createTrainer(trainerDTO);
        facade.updateTrainer(18, trainerDTO1);

        System.out.println(facade.selectTraining(trainingKey));
    }
}
