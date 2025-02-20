package com.mygym.crm.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@ComponentScan("com.mygym.crm")
@PropertySource("classpath:application.properties")
public class Configs {

}
