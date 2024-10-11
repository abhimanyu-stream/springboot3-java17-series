package com.stream.cqrswritedbreaddb.read_query.repository;

import com.stream.cqrswritedbreaddb.read_query.model.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadProductRepository extends JpaRepository<ProductView, Long> {
}
