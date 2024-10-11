package com.stream.cqrs.order.command.services;

import com.stream.cqrs.order.command.commands.CreateOrderCommand;
import com.stream.cqrs.order.command.commands.UpdateOrderCommand;

import java.util.concurrent.CompletableFuture;


public interface OrderCommandService {
    public CompletableFuture<String> createOrder(CreateOrderCommand createOrderCommand);

    public CompletableFuture<String> updateOrder(UpdateOrderCommand updateOrderCommand);
}
