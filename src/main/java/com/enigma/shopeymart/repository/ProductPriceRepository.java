package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, String> {

    //query method
    Optional<ProductPrice> findByProduct_IdAndIsActive(String productId, Boolean active);
}
