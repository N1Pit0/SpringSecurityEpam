package com.mygym.crm.backstages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories("com.mygym.crm.backstages")
public class Application {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(Application.class, args);

        var encoder = app.getBean(BCryptPasswordEncoder.class);

        String adminPassword = encoder.encode("password");

        System.out.println(adminPassword);


    }
}
