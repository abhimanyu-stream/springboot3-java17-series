package com.stream.cqrs.order.command.events.publisher;

import com.stream.cqrs.order.command.dto.OrderMessage;
import com.stream.cqrs.order.command.entities.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void initiatePaymentEvent(Order order) throws JsonProcessingException {
        final String TOPIC = "new_payment_event";

        log.debug("Payment message update on Kafka TOPIC :: {} for Order ID {} Order Status {}", TOPIC, order.getOrderId(), order.getStatus());
        log.info("Payment message update on Kafka TOPIC :: " + TOPIC + " for Order ID " + order.getOrderId());
        OrderMessage paymentMessage = new OrderMessage();
        paymentMessage.setOrderId(order.getOrderId());
        //set user id too
        paymentMessage.setOrderAmount(order.getOrderAmount());
        paymentMessage.setOrderDate(order.getCreatedDate());
        paymentMessage.setStatus(order.getStatus());

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send(TOPIC, objectMapper.writeValueAsString(paymentMessage));
        log.trace("Payment message update on Kafka TOPIC :: {} for Order ID {}", TOPIC, order.getOrderId());
    }
}
