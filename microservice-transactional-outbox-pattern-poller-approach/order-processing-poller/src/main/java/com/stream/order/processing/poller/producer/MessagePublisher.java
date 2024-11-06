package com.stream.order.processing.poller.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stream.order.processing.poller.model.OrderOutBoxEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
@Component
public class MessagePublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Value("${order.poller.topic.name}")

    private String topicName;

    public void publish(OrderOutBoxEvent orderOutBoxEvent) throws JsonProcessingException {
        String payload = new ObjectMapper().writeValueAsString(orderOutBoxEvent);
        //CompletableFuture<SendResult<String, OrderOutBoxEvent>> future = kafkaTemplate.send(topicName, orderOutBoxEvent);

        // here we are sending those order request which are not processed yet and also marked as processed true before in OrderOutBoxEvent Table
        //CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicname,partition, timestamp, key, payload);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, "OrderOutBoxEvent", payload);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + payload +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        payload + "] due to : " + ex.getMessage());
            }
        });
    }
    /**
     * Sends a message to Kafka asynchronously using CompletableFuture
     */
    public CompletableFuture<Void> sendMessage(String topic, String message) {
        // Create a ProducerRecord which represents a message to be sent to Kafka
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

        // Send the message asynchronously and return a CompletableFuture
        return CompletableFuture.runAsync(() -> {
            kafkaTemplate.send(record)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            // If no exception, print the success message
                            System.out.println("Message sent successfully: " + result.getProducerRecord().value());
                        } else {
                            // If there is an exception, print the error message
                            System.err.println("Error sending message: " + ex.getMessage());
                        }
                    });
        });
    }

   /* @PostMapping("/send")
    public CompletableFuture<String> sendMessage(@RequestParam String message) {
        // Send the message asynchronously to the 'test-topic' topic
        return kafkaProducerService.sendMessage("test-topic", message)
                .thenApply(aVoid -> "Message sent successfully");
    }*/
}
