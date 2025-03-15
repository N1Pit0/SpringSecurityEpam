package com.mygym.crm.backstages.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.mygym.crm"})
@Import({AppConfigs.class, HibernateConfigs.class})
public class WebConfig implements WebMvcConfigurer {

}
