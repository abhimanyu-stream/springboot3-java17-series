package com.stream.order_query_service.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * @author vishnu.g
 */
@Data
@Builder
public class GetOrderQuery {
    @Builder.Default
    Integer page = 1;
    @Builder.Default
    Integer size = 10;
    String sort;
}
