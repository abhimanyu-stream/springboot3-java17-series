package com.stream.cqrswritedbreaddb.dto.events;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class ProductUpdatedEvent {
    private  String id;
    private  String name;
    private  int quantity;

    public ProductUpdatedEvent(String id, String name, int quantity) {

        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // Getters
}
