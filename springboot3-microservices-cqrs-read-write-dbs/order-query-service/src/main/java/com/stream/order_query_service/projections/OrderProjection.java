package com.stream.order_query_service.projections;

import com.stream.order_query_service.dto.OrderDto;
import com.stream.order_query_service.dto.OrderResults;
import com.stream.order_query_service.entities.Order;
import com.stream.order_query_service.repositories.OrderRepository;
import com.stream.order_query_service.utils.GetOrderQuery;
import com.stream.order_query_service.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProjection {
    @Autowired
    private final OrderRepository orderRepository;

    @QueryHandler
    public OrderResults handle(GetOrderQuery getOrderQuery) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdDate");
        if (Objects.nonNull(getOrderQuery.getSort())) {
            sort = Utils.parseSortParam(getOrderQuery.getSort());
        }
        if (Objects.isNull(getOrderQuery.getPage())) {
            getOrderQuery.setPage(1);
        }
        if (Objects.isNull(getOrderQuery.getSize())) {
            getOrderQuery.setSize(10);
        }
        Pageable paging = PageRequest.of(getOrderQuery.getPage() - 1, getOrderQuery.getSize(), sort);
        Page<Order> orderPage = orderRepository.findAll(paging);
        OrderResults orderResults = new OrderResults();
        orderResults.setPage(paging.getPageNumber());
        orderResults.setSize(paging.getPageSize());
        orderResults.setTotal(orderPage.getTotalElements());
        orderResults.setOrderDtoList(orderPage.stream().map(order -> toOrderDto(order)).collect(Collectors.toList()));

        return orderResults;

    }

    @QueryHandler
    public Optional<OrderDto> handle(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);
        OrderDto orderDto = null;
        if (optionalOrder.isPresent()) {
            orderDto = new OrderDto();
            BeanUtils.copyProperties(optionalOrder.get(), orderDto);
        }
        return Optional.ofNullable(orderDto);
    }

    private OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        return orderDto;
    }
}
