package com.stream.cqrswritedbreaddb.read_query.controller;


import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stream.cqrswritedbreaddb.read_query.model.ProductView;
import com.stream.cqrswritedbreaddb.read_query.queries.GetProductsQuery;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    private QueryGateway queryGateway;

    @Autowired
    public ProductQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<ProductView> getAllProducts() {
        GetProductsQuery getProductsQuery =
                new GetProductsQuery();

        
       

        return  queryGateway.query(getProductsQuery,
        ResponseTypes.multipleInstancesOf(ProductView.class))
        .join();
    }
}
