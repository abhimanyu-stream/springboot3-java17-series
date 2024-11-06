package com.stream.cqrswritedbreaddb.write_command.repository;

import com.stream.cqrswritedbreaddb.write_command.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteProductRepository extends JpaRepository<Product, Long> {
}
