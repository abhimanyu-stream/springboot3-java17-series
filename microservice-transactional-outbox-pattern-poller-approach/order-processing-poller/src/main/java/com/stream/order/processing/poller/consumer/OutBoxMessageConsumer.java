package com.stream.order.processing.poller.consumer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;


@Slf4j
@Component
public class OutBoxMessageConsumer {



    @KafkaListener(topics = "unprocessed-order-events",groupId = "order-outbox-consumer-group")
    public void consume(String payload){
        log.info("Event consumed {} ",payload);
    }

    @KafkaListener(topics = "unprocessed-order-events", groupId = "order-outbox-consumer-group")
    public void consumeString(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        String message = record.value();
        log.info("Event consumed {} ",message);
        System.out.println("Received message: " + message);

        // Process the message here (e.g., save to database, log, etc.)

        // After successful processing, commit the offset
        acknowledgment.acknowledge();
        System.out.println("Acknowledgment committed for offset: " + record.offset());
    }




}
/**
 * Explanation of the Code
 *
 * @KafkaListener: Listens for messages from the specified Kafka topic (exampleTopic).
 * ConsumerRecord: Provides access to the message and metadata like the offset.
 * Acknowledgment: Allows manual acknowledgment, enabling you to commit the offset only after successful processing.
 * acknowledgment.acknowledge(): Manually commits the offset, acknowledging that the message has been processed.
 *
 *
 * Benefits of Manual Acknowledgment
 * Error Handling: If processing fails, you can choose not to acknowledge the message, allowing it to be reprocessed later.
 * Control Over Offset Management: You commit offsets only after confirming that the message has been processed successfully, giving more control over message handling.
 * Using manual acknowledgment is especially useful in scenarios where processing failures require re-processing or when strict guarantees are necessary for data integrity.
 *
 * */


/**
 * In Spring Boot 3 with Kafka, the @EnableKafka annotation is used to activate Kafka listener annotations, like @KafkaListener, in a consumer application. It enables the application to recognize and handle Kafka messages asynchronously by marking a configuration class or application class with @EnableKafka.
 *
 * When and Why to Use @EnableKafka
 * Required for Kafka Listeners: When you want to use the @KafkaListener annotation to set up a method that listens for messages on specific Kafka topics.
 * Activates Kafka Configuration: It scans for Kafka listener methods and registers them with the Kafka infrastructure within your Spring application.
 *
 * */

