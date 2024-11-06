package com.stream.order.processing.poller.repository;

import com.stream.order.processing.poller.model.OrderOutBoxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderOutBoxEventRepository extends JpaRepository<OrderOutBoxEvent, Long> {
    List<OrderOutBoxEvent> findByProcessedFalse();
}
