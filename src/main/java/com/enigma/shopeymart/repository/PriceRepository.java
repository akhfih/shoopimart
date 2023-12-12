package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<ProductPrice, String> {

}
