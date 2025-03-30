package com.mygym.crm.backstages.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
@EnableAspectJAutoProxy
public class AppConfigs {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean(); // Uses default MessageInterpolator
    }
}
