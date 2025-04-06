package com.timposulabs.springboot.pagination.repository;

import com.timposulabs.springboot.pagination.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
