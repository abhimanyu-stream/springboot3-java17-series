package com.stream.cqrswritedbreaddb.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "writeEntityManagerFactory",
        transactionManagerRef = "writeTransactionManager",
        basePackages = {"com.stream.cqrswritedbreaddb.write_command.repository"})
public class WriteJpaConfig {

}
