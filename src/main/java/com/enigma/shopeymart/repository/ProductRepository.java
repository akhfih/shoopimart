package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
