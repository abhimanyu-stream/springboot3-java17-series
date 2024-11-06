package com.stream.cqrswritedbreaddb.write_command.service;


import com.stream.cqrswritedbreaddb.dto.events.ProductCreatedEvent;
import com.stream.cqrswritedbreaddb.dto.events.ProductUpdatedEvent;
import com.stream.cqrswritedbreaddb.write_command.model.Product;
import com.stream.cqrswritedbreaddb.write_command.repository.WriteProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    private final WriteProductRepository writeProductRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProductCommandService(WriteProductRepository writeProductRepository, ApplicationEventPublisher eventPublisher) {
        this.writeProductRepository = writeProductRepository;
        this.eventPublisher = eventPublisher;
    }

    public Product createProduct(String name, int quantity, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        Product savedProduct = writeProductRepository.save(product);

        // Publish event after saving
        //eventPublisher.publishEvent(new ProductCreatedEvent(this, savedProduct.getId(), savedProduct.getName(), savedProduct.getQuantity()));
        eventPublisher.publishEvent(new ProductCreatedEvent(savedProduct.getId().toString(), savedProduct.getName(), savedProduct.getQuantity(), savedProduct.getPrice()));
        return savedProduct;
    }

    public Product updateProduct(Long id, int quantity) {
        Product product = writeProductRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setQuantity(quantity);

        Product updatedProduct = writeProductRepository.save(product);

        // Publish event after updating
        //eventPublisher.publishEvent(new ProductUpdatedEvent(this, updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getQuantity()));
        eventPublisher.publishEvent(new ProductUpdatedEvent(updatedProduct.getId().toString(), updatedProduct.getName(), updatedProduct.getQuantity()));

        return updatedProduct;
    }
}
