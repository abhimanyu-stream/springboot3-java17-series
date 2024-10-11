package com.stream.order_query_service.utils;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vishnu.g
 */
public class Utils {
    public static Sort parseSortParam(String sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        String[] sortArr = sort.split(",");
        List<Sort.Order> orders = new ArrayList<>();
        for (String s : sortArr) {
            String[] split = s.split(":");
            String property = split[0];
            Sort.Direction direction = Sort.Direction.ASC;
            if (split.length > 1) {
                direction = Sort.Direction.fromString(split[1]);
            }
            orders.add(new Sort.Order(direction, property));
        }
        return Sort.by(orders);
    }
}
