package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest productRequest);

    ProductResponse getById(String id);

    List<Product> getAll();

    ProductResponse update(ProductRequest productRequest);

    void delete(String id);

    ProductResponse createProductAndProductPrice(ProductRequest productRequest);

}
