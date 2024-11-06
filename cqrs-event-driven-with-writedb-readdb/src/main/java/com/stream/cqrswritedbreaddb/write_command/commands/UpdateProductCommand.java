package com.stream.cqrswritedbreaddb.write_command.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private String name;
    private Double price;
    private Integer quantity;
}
