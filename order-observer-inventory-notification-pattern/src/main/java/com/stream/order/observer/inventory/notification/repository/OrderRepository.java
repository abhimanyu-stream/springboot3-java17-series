package com.stream.order.observer.inventory.notification.repository;

import com.stream.order.observer.inventory.notification.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
