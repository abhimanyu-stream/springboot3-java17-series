package com.stream.cqrs_with_writedb_readdb.configuration;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write-db")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read-db")
    public DataSource readDataSource() {
        return DataSourceBuilder.create().build();
    }

   /* @Primary
    //@Order(1)
    @Bean(name = "writeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean writeEntityManagerFactory( EntityManagerFactoryBuilder builder, @Qualifier("writeDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.stream.cqrs_with_writedb_readdb.write_command")
                .persistenceUnit("writePU")
                .build();
    }

   @Bean(name = "readEntityManagerFactory")
   //@Order(2)
    public LocalContainerEntityManagerFactoryBean readEntityManagerFactory( EntityManagerFactoryBuilder builder, @Qualifier("readDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.stream.cqrs_with_writedb_readdb.read_query")
                .persistenceUnit("readPU")
                .build();
    }*/

    /*@Primary
    @Bean(name = "writeTransactionManager")
    public PlatformTransactionManager writeTransactionManager(
            @Qualifier("writeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager readTransactionManager(
            @Qualifier("readEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }*/





    @Primary
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory writeEntityManagerFactory(@Qualifier("writeDataSource") DataSource dataSource) throws SQLException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.stream.cqrs_with_writedb_readdb.write_command");
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName("writePU");
        factory.afterPropertiesSet();

        return factory.getObject();
    }


    @Bean//(name = "entityManagerFactory")
    public EntityManagerFactory readEntityManagerFactory(@Qualifier("readDataSource") DataSource dataSource) throws SQLException {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.stream.cqrs_with_writedb_readdb.read_query");
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName("readPU");
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Primary
    @Bean(name ="writeEntityManager")
    public EntityManager writeEntityManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }


    @Bean("readEntityManager")
    public EntityManager readEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Primary
    @Bean(name = "writeTransactionManager")
    public PlatformTransactionManager writeTransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) throws SQLException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

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
}