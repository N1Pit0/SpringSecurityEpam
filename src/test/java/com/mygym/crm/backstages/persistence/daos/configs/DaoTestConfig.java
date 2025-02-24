package com.mygym.crm.backstages.persistence.daos.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.mygym.crm.backstages.persistence")
@PropertySource("classpath:application.properties")
public class DaoTestConfig {
}
