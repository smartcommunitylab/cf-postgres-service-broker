package org.springframework.cloud.servicebroker.postgresql.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.springframework.cloud.servicebroker.postgresql.repository")
@EnableAutoConfiguration
//@EntityScan(basePackages = {"org.springframework.cloud.servicebroker.postgresql.model"})
public class PostgresqlConfig {

    private static final Logger logger = LoggerFactory.getLogger(BrokerConfiguration.class);

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Bean
    public Connection jdbc() {
        try {
            Connection conn = DriverManager.getConnection(this.jdbcUrl);

            String serviceTable = "CREATE TABLE IF NOT EXISTS service (serviceinstanceid varchar(200) not null default '',"
                    + " servicedefinitionid varchar(200) not null default '',"
                    + " planid varchar(200) not null default '',"
                    + " organizationguid varchar(200) not null default '',"
                    + " spaceguid varchar(200) not null default '')";

            Statement createServiceTable = conn.createStatement();
            createServiceTable.execute(serviceTable);
            return conn;
        } catch (SQLException e) {
            logger.error("Error while creating initial 'service' table", e);
            return null;
        }
    }
	
}
