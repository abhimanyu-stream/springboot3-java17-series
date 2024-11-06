package com.stream.cqrs.order.command.controllers;

import com.stream.cqrs.order.command.commands.CreateOrderCommand;
import com.stream.cqrs.order.command.commands.UpdateOrderCommand;
import com.stream.cqrs.order.command.dto.OrderDto;
import com.stream.cqrs.order.command.exceptions.OrderCreationException;
import com.stream.cqrs.order.command.services.OrderCommandService;
import com.stream.cqrs.order.command.utils.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.stream.cqrs.order.command.utils.ResponseBuilder.successResponse;


@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    @GetMapping
    public ResponseEntity<?> health() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto) throws ExecutionException,
            InterruptedException {

        CreateOrderCommand createOrderCommand = new CreateOrderCommand();
        BeanUtils.copyProperties(orderDto, createOrderCommand);

        log.debug("Processing CreateOrderCommand: {}", createOrderCommand);
        CompletableFuture<String> order = orderCommandService.createOrder(createOrderCommand);

        if (Objects.isNull(order))
            throw new OrderCreationException("Order Creation failed.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.successResponse(order.get(), "Order created successfully...!"));
    }

    @PatchMapping
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto) throws ExecutionException, InterruptedException {
        if (Objects.isNull(orderDto.getOrderId())) {
            throw new IllegalArgumentException("Invalid payload....! ");
        }
        UpdateOrderCommand updateOrderCommand = new UpdateOrderCommand();
        BeanUtils.copyProperties(orderDto, updateOrderCommand);

        CompletableFuture<String> order = orderCommandService.updateOrder(updateOrderCommand);

        if (Objects.isNull(order))
            throw new OrderCreationException("Order Creation failed.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBuilder.successResponse(order.get(), "Order saved successfully...!"));
    }

    @DeleteMapping
    public ResponseEntity<?> cancelOrder(@PathVariable String orderId) throws ExecutionException, InterruptedException {
        if (Objects.isNull(orderId)) {
            throw new IllegalArgumentException("Invalid payload....! ");
        }

        throw new RuntimeException("Cancel order functionality not implemented");
    }


}
