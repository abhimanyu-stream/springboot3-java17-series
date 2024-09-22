package com.stream.cqrs.order.command.services;

import com.stream.cqrs.order.command.commands.CreateOrderCommand;
import com.stream.cqrs.order.command.commands.UpdateOrderCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService{

    private final CommandGateway commandGateway;

    public CompletableFuture<String> createOrder(CreateOrderCommand createOrderCommand) {
        log.debug("Processing CreateOrderCommand: {}", createOrderCommand);
        createOrderCommand.setOrderId(UUID.randomUUID().toString());
        log.info("CreateOrderCommand assigned with new Order Id: {} ", createOrderCommand.getOrderId());
        return commandGateway.send(createOrderCommand);

    }

    public CompletableFuture<String> updateOrder(UpdateOrderCommand updateOrderCommand) {
        log.debug("Processing UpdateOrderCommand: {}", updateOrderCommand);
        return commandGateway.send(updateOrderCommand);
    }
}
