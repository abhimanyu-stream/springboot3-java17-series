package com.stream.order.service.repository;

import com.stream.order.service.model.OrderOutBoxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOutBoxEventRepository extends JpaRepository<OrderOutBoxEvent, Long> {
}
