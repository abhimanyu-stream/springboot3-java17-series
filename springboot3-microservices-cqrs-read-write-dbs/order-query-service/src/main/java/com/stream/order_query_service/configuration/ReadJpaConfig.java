package com.stream.order_query_service.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "readTransactionManager",
        basePackages = {"com.stream.order_query_service.repositories"})
public class ReadJpaConfig {

}
