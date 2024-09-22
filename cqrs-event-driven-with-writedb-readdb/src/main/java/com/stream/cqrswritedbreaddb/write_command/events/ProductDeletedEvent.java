package com.stream.cqrswritedbreaddb.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;


@Setter
@Getter
@Slf4j
public class ProductDeletedEvent {
    private final Long id;
    private final String name;
    private final int quantity;

    public ProductDeletedEvent(Long id, String name, int quantity){

        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

}
