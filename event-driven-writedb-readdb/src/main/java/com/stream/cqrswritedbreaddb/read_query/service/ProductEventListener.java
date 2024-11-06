package com.stream.cqrswritedbreaddb.read_query.service;


import com.stream.cqrswritedbreaddb.dto.ProductCreatedEvent;
import com.stream.cqrswritedbreaddb.dto.ProductUpdatedEvent;
import com.stream.cqrswritedbreaddb.read_query.model.ProductView;
import com.stream.cqrswritedbreaddb.read_query.repository.ReadProductRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ProductEventListener {

    private final ReadProductRepository readProductRepository;

    public ProductEventListener(ReadProductRepository readProductRepository) {
        this.readProductRepository = readProductRepository;
    }

    @EventListener
    public void onProductCreated(ProductCreatedEvent event) {
        ProductView productView = new ProductView();
        productView.setId(Long.valueOf(event.getId()));
        productView.setName(event.getName());
        productView.setQuantity(event.getQuantity());

        readProductRepository.save(productView);
    }

    @EventListener
    public void onProductUpdated(ProductUpdatedEvent event) {
        ProductView productView = readProductRepository.findById(Long.valueOf(event.getId()))
                .orElseThrow(() -> new RuntimeException("ProductView not found"));
        productView.setQuantity(event.getQuantity());

        readProductRepository.save(productView);
    }
}
