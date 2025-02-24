package com.mygym.crm.backstages.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan("com.mygym.crm")
@PropertySource("classpath:application.properties")
public class Configs {

}
