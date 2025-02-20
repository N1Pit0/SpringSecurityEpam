package com.mygym.crm;

import com.mygym.crm.config.Configs;
import com.mygym.crm.persistence.daos.traineedao.TraineeDAOIMPL;
import com.mygym.crm.domain.models.Trainee;
import com.mygym.crm.repositories.services.BaseService;
import com.mygym.crm.services.TraineeServiceIMPL;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Configs.class);
        Trainee trainee = new Trainee();
        trainee.setUserId(1);
        trainee.setAddress("asd");
        trainee.setDateOfBirth(LocalDate.now());
        trainee.setActive(true);
        trainee.setPassword("sad");
        trainee.setFirstName("dsadas");
        trainee.setLastName("sdsad");
        trainee.setUserName("sadsadasdasd");

        TraineeDAOIMPL traineeDAO = (TraineeDAOIMPL) context.getBean("traineeDAOIMPL");

//        System.out.println(traineeDAO.create(trainee));
//        System.out.println(traineeDAO.select(1).orElseThrow(() -> new NoTraineeException("")).getFirstName());

        BaseService<Trainee, Integer> service = context.getBean(TraineeServiceIMPL.class);
        service.create(trainee);
        System.out.println(service.getById(1).orElse(null).getAddress());
//        TrainingKey trainingKey = new TrainingKey(1,2);
//        Training training = new Training(trainingKey);
//        training.setTrainingName("dasdasd");
//
//        TrainingDAO trainingDAO = context.getBean(TrainingDAOIMPL.class);
//        trainingDAO.create(training);
//        System.out.println(trainingDAO.select(trainingKey).orElseThrow(() -> new NoTrainingException("No training found")).getTrainingName());

    }
}
