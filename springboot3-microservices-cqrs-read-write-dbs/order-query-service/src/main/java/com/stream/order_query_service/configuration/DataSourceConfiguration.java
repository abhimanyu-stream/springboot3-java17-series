package com.stream.order_query_service.configuration;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

   /* @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write-db")
    public DataSource writeDataSource() {

        return DataSourceBuilder.create().build();
    }*/

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read-db")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }




   /* @Primary
    @Bean(name = "writeEntityManagerFactory")
    public EntityManagerFactory writeEntityManagerFactory(@Qualifier("writeDataSource") DataSource dataSource) throws SQLException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.stream.cqrs.order.command.entities");
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName("writePU");
        factory.afterPropertiesSet();

        return factory.getObject();
    }*/


   // @Bean(name = "readEntityManagerFactory")
   @Bean(name = "entityManagerFactory")
    public EntityManagerFactory readEntityManagerFactory(@Qualifier("readDataSource") DataSource dataSource) throws SQLException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.stream.order_query_service.entities");
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName("readPU");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /*@Primary
    @Bean(name ="writeEntityManager")
    public EntityManager writeEntityManager(@Qualifier("writeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }*/


    @Bean(name ="readEntityManager")
    public EntityManager readEntityManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

   /* @Primary
    @Bean(name = "writeTransactionManager")
    public PlatformTransactionManager writeTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) throws SQLException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }*/

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager readTransactionManager(EntityManagerFactory entityManagerFactory) throws SQLException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }
     @Bean
    public CommandBus commandBus() {
        // Create and configure your custom CommandBus
        return SimpleCommandBus.builder().build();
    }

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        // Create the CommandGateway using the custom CommandBus
        return DefaultCommandGateway.builder().commandBus(commandBus).build();
    }
}