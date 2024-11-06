package com.stream.order.processing.poller.configuration;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"order-outbox-consumer-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stream.order.processing.poller.model,com.stream.order.processing.poller.transfer");// Only on Consumer side
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");



        return props;
    }




    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean("kafkaListenerContainerFactoryOrderOutBoxEvent")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryOrderOutBoxEvent(DefaultKafkaConsumerFactory<String, String> consumerFactory, KafkaTemplate<String, String> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);
        // Other customizations (e.g., concurrency, error handling) can be added here
        return factory;
    }





   /* @Bean
    public ConsumerFactory<String, OrderOutBoxEvent> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean("kafkaListenerContainerFactoryOrderOutBoxEvent")
    public ConcurrentKafkaListenerContainerFactory<String, OrderOutBoxEvent> kafkaListenerContainerFactoryOrderOutBoxEvent(DefaultKafkaConsumerFactory<String, OrderOutBoxEvent> consumerFactory, KafkaTemplate<String, OrderOutBoxEvent> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, OrderOutBoxEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);
        // Other customizations (e.g., concurrency, error handling) can be added here
        return factory;
    }*/
}
