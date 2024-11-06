package com.stream.cqrswritedbreaddb.read_query.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Entity

@Table(name = "product_view")
@Data
@Builder
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ProductView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_key")
    private Long id;
    private String name;
    private int quantity;
    private Double price;

}
