package com.mygym.crm.backstages.persistence.storages.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.mygym.crm.backstages.persistence.storages")
@PropertySource("classpath:application.properties")
public class StorageTestConfig {
}
