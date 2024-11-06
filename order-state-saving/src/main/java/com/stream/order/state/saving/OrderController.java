package com.stream.order.state.saving;

import com.stream.order.state.saving.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {


    private static OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService=orderService;
    }

    @PostMapping
    public void createOrder(){
        orderService.createOrderWithStates();
    }
}
