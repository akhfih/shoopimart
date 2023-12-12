package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final StoreService storeService;
    private final ProductPriceService productPriceService;

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription()).build();
        productRepository.save(product);
        return getResponse(product);
    }

    private static ProductResponse getResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .description(product.getDescription()).build();
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = productRepository.findById(id).get();
        if(product != null){
            return ProductResponse.builder()
                    .id(product.getId())
                    .nameProduct(product.getName())
                    .description(product.getDescription()).build();
        }

        return null;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
//        List<Product> products = productRepository.findAll();
//        return products.stream().map(
//                product -> ProductResponse.builder()
//                        .id(product.getId())
//                        .nameProduct(product.getName())
//                        .description(product.getDescription())
//                        .productPriceList(product.getProductPriceList())
//                        .build()).collect(Collectors.toList());

    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        ProductResponse currentCustomerId = getById(productRequest.getProductId());
        if (currentCustomerId != null) {
            Product product = Product.builder()
                    .name(productRequest.getProductName())
                    .description(productRequest.getDescription()).build();
            productRepository.save(product);
            return getResponse(product);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)// jika semua gagal akan diexception ro
    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {

        Store store = storeService.getById(productRequest.getStoreId().getId());

        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .store(store)
                .build();

        productPriceService.create(productPrice);

        return ProductResponse.builder()
                .id(product.getId())
                .nameProduct(product.getName())
                .description(product.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .noSiup(store.getNoSiup())
                        .storeName(store.getName())
                        .address(store.getAddress())
                        .phone(store.getMobilePhone())
                        .build())
                .build();

    }
}
