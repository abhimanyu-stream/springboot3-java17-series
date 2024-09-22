package com.stream.cqrswritedbreaddb.write_command.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteProductCommand {

    @TargetAggregateIdentifier
    private String productId;

}
