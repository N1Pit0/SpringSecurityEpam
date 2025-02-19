package com.mygym.crm;

import com.mygym.crm.config.Configs;
import com.mygym.crm.daos.traineedao.TraineeDAOIMPL;
import com.mygym.crm.models.Trainee;
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

        traineeDAO.create(trainee);
        System.out.println(traineeDAO.select(1).getAddress());
    }
}
