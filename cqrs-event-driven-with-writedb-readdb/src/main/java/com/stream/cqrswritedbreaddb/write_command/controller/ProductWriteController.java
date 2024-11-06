package com.stream.cqrswritedbreaddb.write_command.controller;

import com.stream.cqrswritedbreaddb.dto.ProductWriteRequest;
import com.stream.cqrswritedbreaddb.write_command.commands.CreateProductCommand;
import com.stream.cqrswritedbreaddb.write_command.commands.DeleteProductCommand;
import com.stream.cqrswritedbreaddb.write_command.commands.UpdateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/write/api")
public class ProductWriteController {


    
    private CommandGateway commandGateway;
    @Autowired
    public ProductWriteController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String addProduct(@RequestBody ProductWriteRequest productWriteRequest) {

        CreateProductCommand createProductCommand =
                CreateProductCommand.builder()
                        .productId(UUID.randomUUID().toString())
                        .name(productWriteRequest.getName())
                        .price(productWriteRequest.getPrice())
                        .quantity(productWriteRequest.getQuantity())
                        .build();
        return commandGateway.sendAndWait(createProductCommand);
    }

    @PutMapping ("/update")
    public String updateProduct(@RequestBody ProductWriteRequest productWriteRequest) {

        UpdateProductCommand updateProductCommand =
                UpdateProductCommand.builder()
                        .name(productWriteRequest.getName())
                        .price(productWriteRequest.getPrice())
                        .quantity(productWriteRequest.getQuantity())
                        .build();
        return commandGateway.sendAndWait(updateProductCommand);
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@PathVariable String productId) {

        DeleteProductCommand deleteProductCommand =
                DeleteProductCommand.builder()
                        .productId(productId)
                        .build();
        return commandGateway.sendAndWait(deleteProductCommand);
    }



}
