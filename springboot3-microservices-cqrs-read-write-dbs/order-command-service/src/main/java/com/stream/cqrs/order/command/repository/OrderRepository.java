package com.stream.cqrs.order.command.repository;

import com.stream.cqrs.order.command.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByOrderId(String orderId);
}
