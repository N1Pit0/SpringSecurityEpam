package com.mygym.crm.backstages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(Application.class, args);

        var encoder = app.getBean(BCryptPasswordEncoder.class);

        String adminPassword = encoder.encode("password");

        System.out.println(adminPassword);


    }
}
