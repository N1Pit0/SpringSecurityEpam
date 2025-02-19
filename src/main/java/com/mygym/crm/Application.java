package com.mygym.crm;

import com.mygym.crm.config.Configs;
import com.mygym.crm.daos.traineedao.TraineeDAOIMPL;
import com.mygym.crm.daos.trainingdao.TrainingDAOIMPL;
import com.mygym.crm.exceptions.NoTraineeException;
import com.mygym.crm.exceptions.NoTrainingException;
import com.mygym.crm.models.Trainee;
import com.mygym.crm.models.Training;
import com.mygym.crm.models.TrainingKey;
import com.mygym.crm.repositories.daorepositories.TrainingDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configs.class);
//        Trainee trainee = new Trainee();
//        trainee.setUserId(1);
//        trainee.setAddress("asd");
//        trainee.setDateOfBirth(LocalDate.now());
//        trainee.setActive(true);
//        trainee.setPassword("sad");
//        trainee.setFirstName("dsadas");
//        trainee.setLastName("sdsad");
//        trainee.setUserName("sadsadasdasd");
//
//        TraineeDAOIMPL traineeDAO = (TraineeDAOIMPL) context.getBean("traineeDAOIMPL");
//
//        System.out.println(traineeDAO.create(trainee));
//        System.out.println(traineeDAO.select(1).orElseThrow(() -> new NoTraineeException("")).getFirstName());
        TrainingKey trainingKey = new TrainingKey(1,2);
        Training training = new Training(trainingKey);
        training.setTrainingName("dasdasd");

        TrainingDAO trainingDAO = context.getBean(TrainingDAOIMPL.class);
        trainingDAO.create(training);
        System.out.println(trainingDAO.select(trainingKey).orElseThrow(() -> new NoTrainingException("No training found")).getTrainingName());

    }
}
