package com.stream.cqrswritedbreaddb.write_command.aggregate;

import com.stream.cqrswritedbreaddb.dto.events.ProductCreatedEvent;
import com.stream.cqrswritedbreaddb.write_command.commands.CreateProductCommand;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.springframework.beans.BeanUtils;

public class ProductCreateAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private Double price;
    private Integer quantity;

    @CommandHandler
    public ProductCreateAggregate(CreateProductCommand createProductCommand) {
        //You can perform all the validations
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand,productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    public ProductCreateAggregate() {
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.quantity = productCreatedEvent.getQuantity();
        this.productId = productCreatedEvent.getId();
        this.price = productCreatedEvent.getPrice();
        this.name = productCreatedEvent.getName();
    }
}
