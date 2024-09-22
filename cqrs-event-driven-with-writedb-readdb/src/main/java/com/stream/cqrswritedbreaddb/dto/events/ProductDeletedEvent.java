package com.stream.cqrswritedbreaddb.dto.events;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Setter
@Getter
@Slf4j
public class ProductDeletedEvent {
    private String id;
    private String name;
    private int quantity;

    public ProductDeletedEvent(String id, String name, int quantity){

        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

}
