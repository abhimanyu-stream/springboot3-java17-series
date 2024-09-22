package com.stream.cqrswritedbreaddb.read_query.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stream.cqrswritedbreaddb.read_query.model.ProductView;


@Repository
public interface ProductReadRepository extends JpaRepository<ProductView, Long>{

}
