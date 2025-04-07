package com.mygym.crm.backstages.config.healtindicators;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class LoggingServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            LoggerContext loggerContext = LoggerContext.getContext(false);
            Configuration configuration = loggerContext.getConfiguration();

            if (configuration == null) {
                return Health.down().withDetail("Log4j2 Status", "Configuration not initialized").build();
            }

            boolean appendersAvailable = !configuration.getAppenders().isEmpty();
            if (appendersAvailable) {
                boolean loggerWorking = testLogging();
                if (loggerWorking) {
                    return Health.up()
                            .withDetail("Log4j2 Status", "Available")
                            .withDetail("Active Appenders", configuration.getAppenders().keySet())
                            .build();
                } else {
                    return Health.down()
                            .withDetail("Log4j2 Status", "Logging failed")
                            .build();
                }
            } else {
                return Health.down().withDetail("Log4j2 Status", "No active appenders").build();
            }
        } catch (Exception e) {
            return Health.down().withDetail("Log4j2 Status", "Failed to check Log4j2 health").withException(e).build();
        }
    }

    private boolean testLogging() {
        try {
            org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(LoggingServiceHealthIndicator.class);
            logger.info("Testing Log4j2 Health Indicator - This is a test log entry");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
