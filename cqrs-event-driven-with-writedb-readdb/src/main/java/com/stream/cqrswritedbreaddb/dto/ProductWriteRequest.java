package com.stream.cqrswritedbreaddb.dto;


import lombok.Data;

@Data
public class ProductWriteRequest {

    private String name;
    private Double price;
    private Integer quantity;
}
