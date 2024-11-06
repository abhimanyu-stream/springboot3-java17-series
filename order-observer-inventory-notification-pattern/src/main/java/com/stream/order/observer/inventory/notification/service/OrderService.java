package com.stream.order.observer.inventory.notification.service;

import com.stream.order.observer.inventory.notification.model.Order;
import com.stream.order.observer.inventory.notification.observer.OrderCreatedEvent;
import com.stream.order.observer.inventory.notification.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
//Create OrderService
//The OrderService will publish events when a new order is created.
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public OrderService(ApplicationEventPublisher eventPublisher, OrderRepository orderRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Order order) {
        Order savedOrder = orderRepository.save(order);


        eventPublisher.publishEvent(new OrderCreatedEvent(savedOrder));
        return savedOrder;
    }

    public void updateOrderStatusByListeningKafkaTopic(){
        // find Order by orderId
        // update [private String status;// CREATED PACKAGED SHIPPED DELIVERED RETURNED NOT-DELIVERED] in repository according to values in related topic like order-created, order-updates. [also update any related topic after it]
        //
    }
}