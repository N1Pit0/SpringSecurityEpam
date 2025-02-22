package com.mygym.crm;

import com.mygym.crm.config.Configs;
import com.mygym.crm.core.services.TrainerServiceIMPL;
import com.mygym.crm.core.services.TrainingServiceIMPL;
import com.mygym.crm.core.services.dtos.TraineeDTO;
import com.mygym.crm.core.services.dtos.TrainerDTO;
import com.mygym.crm.domain.models.Trainer;
import com.mygym.crm.domain.models.TrainingKey;
import com.mygym.crm.persistence.daos.traineedao.TraineeDAOIMPL;
import com.mygym.crm.domain.models.Trainee;
import com.mygym.crm.repositories.daorepositories.TrainerDAO;
import com.mygym.crm.repositories.services.BaseService;
import com.mygym.crm.core.services.TraineeServiceIMPL;
import com.mygym.crm.repositories.services.TrainerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configs.class);

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

        TraineeServiceIMPL traineeService = (TraineeServiceIMPL) context.getBean("traineeServiceIMPL");
        TrainerServiceIMPL trainerService = (TrainerServiceIMPL) context.getBean("trainerServiceIMPL");
        TrainingServiceIMPL trainingServiceIMPL = context.getBean(TrainingServiceIMPL.class);

        TrainingKey trainingKey = new TrainingKey();
        trainingKey.setTraineeId(6);
        trainingKey.setTrainerId(18);

//        traineeService.create(traineeDTO);
//        traineeService.create(traineeDTO1);
//        trainerService.create(trainerDTO);

        System.out.println(traineeService.getById(1).orElse(null).toString());
        System.out.println(traineeService.getById(2).orElse(null).toString());
        System.out.println(trainerService.getById(4).orElse(null).toString());
        System.out.println(trainingServiceIMPL.getById(trainingKey).orElse(null).toString());


    }
}
