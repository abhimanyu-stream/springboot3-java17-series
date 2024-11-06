package com.stream.order_query_service.controllers;


import com.stream.order_query_service.dto.OrderDto;
import com.stream.order_query_service.dto.OrderResults;
import com.stream.order_query_service.services.OrderQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author vishnu.g
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderQueryController {
    private final OrderQueryService orderQueryService;

    @GetMapping
    public CompletableFuture<OrderResults> retrieveAllOrders(
            @RequestParam(required = false, name = "page") Integer page,
            @RequestParam(required = false, name = "size") Integer size,
            @RequestParam(name = "sort", required = false) String sort)
    {
        return orderQueryService.retrieveAllOrders(page, size, sort);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") String orderId)
            throws ExecutionException, InterruptedException
    {
        CompletableFuture<Optional<OrderDto>> orderById = orderQueryService.getOrderById(orderId);

        OrderDto orderDto = orderById.get().orElseThrow(() -> new NoSuchElementException("Order not found."));

        return ResponseEntity.ok(orderDto);

    }
}
