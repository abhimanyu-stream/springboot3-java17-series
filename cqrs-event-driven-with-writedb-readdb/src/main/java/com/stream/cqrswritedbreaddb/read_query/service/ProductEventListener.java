package com.stream.cqrswritedbreaddb.read_query.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.stream.cqrswritedbreaddb.dto.events.ProductCreatedEvent;
import com.stream.cqrswritedbreaddb.dto.events.ProductUpdatedEvent;
import com.stream.cqrswritedbreaddb.read_query.model.ProductView;
import com.stream.cqrswritedbreaddb.read_query.repository.ProductReadRepository;


@Service
public class ProductEventListener {

    private final ProductReadRepository productReadRepository;

    public ProductEventListener(ProductReadRepository productReadRepository) {
        this.productReadRepository = productReadRepository;
    }

    @EventListener
    public void onProductCreated(ProductCreatedEvent event) {
        ProductView productView = new ProductView();
        productView.setId(Long.valueOf(event.getId()));
        productView.setName(event.getName());
        productView.setQuantity(event.getQuantity());

        productReadRepository.save(productView);
    }

    @EventListener
    public void onProductUpdated(ProductUpdatedEvent event) {
        ProductView productView = productReadRepository.findById(Long.valueOf(event.getId()))
                .orElseThrow(() -> new RuntimeException("ProductView not found"));
        productView.setQuantity(event.getQuantity());

        productReadRepository.save(productView);
    }

}
