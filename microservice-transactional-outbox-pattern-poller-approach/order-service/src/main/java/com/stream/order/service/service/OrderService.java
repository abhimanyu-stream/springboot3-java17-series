package com.stream.order.service.service;

import com.stream.order.service.model.Order;
import com.stream.order.service.model.OrderOutBoxEvent;
import com.stream.order.service.repository.OrderOutBoxEventRepository;
import com.stream.order.service.repository.OrderRepository;
import com.stream.order.service.transfer.OrderRequest;
import com.stream.order.service.util.OrderRequestToOrder;
import com.stream.order.service.util.OrderToOrderOutBoxEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {


    private OrderRequestToOrder orderRequestToOrder;
    private OrderRepository orderRepository;
    private OrderOutBoxEventRepository orderOutBoxEventRepository;
    private OrderToOrderOutBoxEvent orderToOrderOutBoxEvent;


    @Autowired
    public OrderService(OrderRequestToOrder orderRequestToOrder, OrderRepository orderRepository, OrderOutBoxEventRepository orderOutBoxEventRepository, OrderToOrderOutBoxEvent orderToOrderOutBoxEvent) {
        this.orderRequestToOrder = orderRequestToOrder;
        this.orderRepository = orderRepository;
        this.orderOutBoxEventRepository = orderOutBoxEventRepository;
        this.orderToOrderOutBoxEvent = orderToOrderOutBoxEvent;
    }

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {

        Order order = orderRequestToOrder.map(orderRequest);
        order = orderRepository.save(order);
//Note:- both save will be done or rollback both if exception occurred, as both are running within one transaction
        OrderOutBoxEvent outbox = orderToOrderOutBoxEvent.map(order);
        orderOutBoxEventRepository.save(outbox);

        return order;
    }
}
