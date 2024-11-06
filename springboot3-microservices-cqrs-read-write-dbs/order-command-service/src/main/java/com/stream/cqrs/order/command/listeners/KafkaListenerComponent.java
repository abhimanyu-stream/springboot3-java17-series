package com.stream.cqrs.order.command.listeners;

import com.stream.cqrs.order.command.commands.UpdateOrderCommand;
import com.stream.cqrs.order.command.dto.PaymentMessage;
import com.stream.cqrs.order.command.utils.OrderStatus;
import com.stream.cqrs.order.command.utils.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListenerComponent {

    private final ObjectMapper objectMapper;
    private final CommandGateway commandGateway;

    @KafkaListener(groupId = "payment-events-1", topics = "payment_status_events", containerFactory = "kafkaListenerContainerFactory")
    public void listenFromOrderTopic(String msg) throws JsonProcessingException, InterruptedException {
        executeUpdateCommand(msg);
    }

    private void executeUpdateCommand(String msg) throws JsonProcessingException, InterruptedException {
        PaymentMessage paymentMessage = objectMapper.readValue(msg, PaymentMessage.class);
        log.info("Updating order status for order id : " + paymentMessage.getOrderId());
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand();
        updateOrderCommand.setOrderId(paymentMessage.getOrderId());
        updateOrderCommand.setTransactionId(paymentMessage.getTransactionId());
        if (PaymentStatus.PAYMENT_SUCCESS.name().equals(paymentMessage.getStatus())) {
            updateOrderCommand.setStatus(OrderStatus.PLACED.name());
        } else {
            updateOrderCommand.setStatus(OrderStatus.CANCELLED.name());
        }
        commandGateway.sendAndWait(updateOrderCommand);// why it is so
    }
}
