package com.stream.cqrswritedbreaddb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Data
@Builder
@Slf4j
public class ProductUpdatedEvent {
    private final Long id;
    private final String name;
    private final int quantity;

    public ProductUpdatedEvent(Long id, String name, int quantity) {

        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // Getters
}
