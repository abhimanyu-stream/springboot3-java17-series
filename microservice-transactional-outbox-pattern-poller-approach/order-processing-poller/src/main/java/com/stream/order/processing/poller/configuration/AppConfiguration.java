package com.stream.order.processing.poller.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AppConfiguration {


    @Value("${order.poller.topic.name}")
    private String topicUnProcessed;


    @Bean("payment-transactions-topic")
    public NewTopic unProcessedTopic() {
        return TopicBuilder.name("topicUnProcessed")
                .partitions(3)
                .replicas(1)
                .build();
    }

}
