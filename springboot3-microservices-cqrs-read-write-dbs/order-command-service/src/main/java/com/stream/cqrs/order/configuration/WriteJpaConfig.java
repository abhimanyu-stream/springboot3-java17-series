package com.stream.cqrs.order.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "writeEntityManagerFactory",
        transactionManagerRef = "writeTransactionManager",
        basePackages = { "com.stream.cqrs.order.command.repository" })
public class WriteJpaConfig {

}
