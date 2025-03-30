package com.mygym.crm.backstages.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Customer Relationship Manager API",
                version = "1.0.0",
                description = "API documentation for managing trainees, trainers, trainings and trainingTypes"
        )
)
public class OpenAPIConfig {
}