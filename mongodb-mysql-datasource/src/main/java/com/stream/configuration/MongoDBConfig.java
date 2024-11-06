package com.stream.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.stream.repository.mongodb",
        mongoTemplateRef = "mongoTemplate"
)
/*public class MongoDBConfig {
    // MongoDB configuration is picked up automatically by Spring Boot based on spring.data.mongodb.uri


}*/
public class MongoDBConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return "ecommerce"; // replace with your MongoDB database name
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }
}

/***
 * Explanation of MongoDBConfig Class
 * @Configuration: Marks this class as a configuration for MongoDB.
 * @EnableMongoRepositories: Enables MongoDB repositories and scans the specified package (com.stream.repository.mongodb) for repository interfaces.
 * The mongoTemplateRef = "mongoTemplate" tells Spring to use this mongoTemplate bean in the specified package.
 * MongoClient: Configures the MongoDB client using the URI from application.properties or application.yml.
 * MongoTemplate: Provides the MongoTemplate bean, which is used for custom MongoDB operations that are beyond the basic CRUD provided by MongoDB repositories.
 * With this setup, the application will automatically pick up MongoDB configurations from the application.properties file, while also allowing you to customize MongoDB settings through the MongoTemplate.
 *
 */