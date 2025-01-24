package com.stream.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfiguration {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
    /**
     * We define a method named jdbcTemplate annotated with @Bean.
     * The method takes a DataSource parameter, which Spring Boot automatically injects based on your data source configuration.
     * We create a new JdbcTemplate instance and set its data source.
     * */
    /**
     * Creating the JdbcClient Bean: Spring Boot automatically discovers the DB connection properties from your application.properties or application.yml file
     * and creates the JdbcClient bean during application startup.
     * You can then inject this bean into any class where you need to perform database operations.
     * */
}
