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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        if (product != null) {
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

    @Transactional(rollbackOn = Exception.class)// jika semua gagal akan diexception rollback
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
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .address(store.getAddress())
                        .phone(store.getMobilePhone())
                        .build())
                .build();

    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        //Specification untuk menentukan kriteria pencarian, disini criteria pencarian ditandakan dengan root, root yang dimaksud adalah entitiy product
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            //Join digunakan utk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPrices = root.join("productPrices");
            //Predicate digunakan untuk menggunakan LIKE dimana nanti kita akan menggunakan kondisi pencarian parameter
            //disini kita akan mecari nama product atau harga dibawahnya, makanya menggunkan leesThanOrEqual
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
            }
            //kode return mengembalikan query dimana pada dasarnya kita membangun klausa where yang sudah ditentukan dari predicata atau kriteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(specification, pageable);
        //ini digunakan untuk mengiterasi daftar product yg disimpan dalam object
        List<ProductResponse> productResponses = new ArrayList<>();// optional ini untuk mencari harga yang aktif
        for (Product product : products.getContent()) {
            Optional<ProductPrice> productPrice = product.getProductPrices()
                    .stream()
                    .filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty()) continue; // kondisi ini digunakan untuk memeriksa apakah productPricenya kosong atau tidak, kalau tidak maka di skip
            Store store = productPrice.get().getStore(); // ini digunakan untuk jika harga product yang aktif ditemukan di store
            productResponses.add(ProductResponse.builder()
                    .id(product.getId())
                    .nameProduct(product.getName())
                    .description(product.getDescription())
                    .price(productPrice.get().getPrice())
                    .stock(productPrice.get().getStock())
                    .store(StoreResponse.builder()
                            .id(store.getId())
                            .storeName(store.getName())
                            .noSiup(store.getNoSiup())
                            .address(store.getAddress())
                            .phone(store.getMobilePhone())
                            .build())
                    .build());
        }

        return new PageImpl<>(productResponses, pageable, products.getTotalElements());


}
}
