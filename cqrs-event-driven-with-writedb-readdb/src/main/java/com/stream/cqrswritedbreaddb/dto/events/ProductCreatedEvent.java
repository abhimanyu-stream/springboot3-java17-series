package com.stream.cqrswritedbreaddb.dto.events;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Builder
@Slf4j

public class ProductCreatedEvent {
    private  String id;
    private  String name;
    private  int quantity;
    private Double price;
    public ProductCreatedEvent(){}

    public ProductCreatedEvent(String id, String name, int quantity, Double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters

}
