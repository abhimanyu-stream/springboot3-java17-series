package com.stream.cqrswritedbreaddb.dto;


import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Builder
@Slf4j
public class ProductUpdatedEvent {
    private final String id;
    private final String name;
    private final int quantity;

    public ProductUpdatedEvent(String id, String name, int quantity) {

        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // Getters
}
