package com.stream.order_query_service.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author vishnu.g
 */
@Data
@Entity(name = "order_products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long orderProductsId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_quantity")
    private int quantity;

    @Column(name = "product_price")
    private double price;
}
