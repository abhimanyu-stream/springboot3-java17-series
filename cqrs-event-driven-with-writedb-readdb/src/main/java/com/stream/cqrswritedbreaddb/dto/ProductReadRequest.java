package com.stream.cqrswritedbreaddb.dto;


import lombok.Data;

@Data
public class ProductReadRequest {

    private String name;
    private Double price;
    private Integer quantity;

}
