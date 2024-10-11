package com.stream.cqrswritedbreaddb.write_command.commands;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
public class CreateProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private String name;
    private Double price;
    private Integer quantity;
}
