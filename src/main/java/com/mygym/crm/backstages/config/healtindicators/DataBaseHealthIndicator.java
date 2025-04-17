package com.mygym.crm.backstages.config.healtindicators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component("database")
public class DataBaseHealthIndicator implements HealthIndicator {
    private final DataSource dataSource;

    @Autowired
    public DataBaseHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        try (Connection connection = dataSource.getConnection()) {

            return Health.up()
                    .withDetail("Database Status", "Up and Running")
                    .withDetail("Database URL", dataSource.getConnection().getMetaData().getURL())
                    .withDetail("Database User", dataSource.getConnection().getMetaData().getUserName())
                    .build();
        } catch (Exception e) {

            return Health.down()
                    .withDetail("Database Status", "Service Unavailable")
                    .withException(e)
                    .build();
        }
    }
}
