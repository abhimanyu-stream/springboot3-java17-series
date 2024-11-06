package com.stream.order.service.transfer;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderRequest {

    private String name;
    private String customerId;
    private String productType;
    private int quantity;
    private BigDecimal price;


}
