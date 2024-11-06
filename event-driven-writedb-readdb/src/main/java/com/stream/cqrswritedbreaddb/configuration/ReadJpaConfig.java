package com.stream.cqrswritedbreaddb.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "readTransactionManager",
        basePackages = {"com.stream.cqrswritedbreaddb.read_query.repository"})
public class ReadJpaConfig {

}
