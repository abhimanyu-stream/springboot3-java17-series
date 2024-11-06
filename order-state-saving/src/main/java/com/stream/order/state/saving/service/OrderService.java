package com.stream.order.state.saving.service;

import com.stream.order.state.saving.model.Order;
import com.stream.order.state.saving.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrderWithStates() {
        Order order = new Order();

        LinkedHashSet<String> states = new LinkedHashSet<>();
        states.add("delivered");
        states.add("order-initiated");
        states.add("paid");
        //states.add("packaged");
        states.add("shipped");
        //states.add("delivered");

        order.setOrderStates(states);
        return orderRepository.save(order);
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
