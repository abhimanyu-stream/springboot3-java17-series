package com.stream.order_query_service.services;


import com.stream.order_query_service.dto.OrderDto;
import com.stream.order_query_service.dto.OrderResults;
import com.stream.order_query_service.utils.GetOrderQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {
    private final QueryGateway queryGateway;

    @Override
    public CompletableFuture<OrderResults> retrieveAllOrders(Integer page, Integer size, String sort) {
        GetOrderQuery getOrderQuery = GetOrderQuery.builder().page(page).size(size).sort(sort).build();
        return queryGateway.query(getOrderQuery, ResponseTypes.instanceOf(OrderResults.class));
    }

    @Override
    public CompletableFuture<Optional<OrderDto>> getOrderById(String orderId) {
        return queryGateway.query(orderId, ResponseTypes.optionalInstanceOf(OrderDto.class));
    }
}
