package com.stream.cqrswritedbreaddb.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;



@Setter
@Getter
@Slf4j
public class ProductDeletedEvent {
    private final String id;
    private final String name;
    private final int quantity;

    public ProductDeletedEvent(String id, String name, int quantity){

        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

}
