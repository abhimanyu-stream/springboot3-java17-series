package com.stream.order_query_service.services;


import com.stream.order_query_service.dto.OrderDto;
import com.stream.order_query_service.dto.OrderResults;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author vishnu.g
 */
public interface OrderQueryService {
    public CompletableFuture<OrderResults> retrieveAllOrders(Integer page, Integer size, String sort);

    public CompletableFuture<Optional<OrderDto>> getOrderById(String orderId);
}
