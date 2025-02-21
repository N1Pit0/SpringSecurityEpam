package com.mygym.crm;

import com.mygym.crm.config.Configs;
import com.mygym.crm.core.services.TrainerServiceIMPL;
import com.mygym.crm.core.services.dtos.TraineeDTO;
import com.mygym.crm.core.services.dtos.TrainerDTO;
import com.mygym.crm.domain.models.Trainer;
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

        TrainerDTO trainerDTO = new TrainerDTO();

        trainerDTO.setFirstName("John");
        trainerDTO.setLastName("Doe");
        trainerDTO.setActive(true);

        TrainerServiceIMPL trainerService = (TrainerServiceIMPL) context.getBean("trainerServiceIMPL");

        trainerService.create(trainerDTO);

        System.out.println(trainerService.getById(1).orElse(null));

    }
}
