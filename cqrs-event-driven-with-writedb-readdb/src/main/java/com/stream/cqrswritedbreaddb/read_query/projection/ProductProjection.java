package com.stream.cqrswritedbreaddb.read_query.projection;


import com.stream.cqrswritedbreaddb.read_query.model.ProductView;
import com.stream.cqrswritedbreaddb.read_query.queries.GetProductsQuery;
import com.stream.cqrswritedbreaddb.read_query.repository.ProductReadRepository;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {

    private ProductReadRepository productReadRepository;

    @Autowired
    public ProductProjection(ProductReadRepository productReadRepository) {
        this.productReadRepository = productReadRepository;
    }

    @QueryHandler
    public List<ProductView> handle(GetProductsQuery getProductsQuery) {
        List<ProductView> products = productReadRepository.findAll();
                

        return products.stream()
        .map(product -> ProductView
                .builder()
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .name(product.getName())
                .build())
        .collect(Collectors.toList());
    }
}
