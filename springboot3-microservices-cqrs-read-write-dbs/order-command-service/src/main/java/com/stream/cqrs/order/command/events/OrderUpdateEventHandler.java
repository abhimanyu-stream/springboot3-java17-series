package com.stream.cqrs.order.command.events;

import com.stream.cqrs.order.command.entities.Order;
import com.stream.cqrs.order.command.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderUpdateEventHandler {

    private final OrderRepository orderRepository;

    @EventHandler
    public void on(OrderUpdateEvent orderUpdateEvent) {
        log.debug("Handling {} event: {}", orderUpdateEvent.getClass().getSimpleName(), orderUpdateEvent.getOrderId());
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderUpdateEvent.getOrderId());
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(orderUpdateEvent.getStatus());
            order.setUpdatedDate(LocalDateTime.now().toString());
            orderRepository.save(order);
        }
        log.trace("Done handling {} event: {}", orderUpdateEvent.getClass().getSimpleName(), orderUpdateEvent.getOrderId());
    }
}
