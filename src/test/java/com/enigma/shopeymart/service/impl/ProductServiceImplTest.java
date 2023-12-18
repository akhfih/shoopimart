package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductPriceService productPriceService = mock(ProductPriceService.class);
    private final StoreService storeService = mock(StoreService.class);
    private final ProductService productService = new ProductServiceImpl(productRepository,storeService,productPriceService);

    @BeforeEach
    public void setUp(){
        //reset mock behavior
        reset(productRepository, storeService, productPriceService);
    }


    @Test
    void createProductAndProductPrice() {
        //sotre request
        Store dummyStore = new Store();
        dummyStore.setId("store1");
        dummyStore.setName("Berkah Selalu");
        dummyStore.setNoSiup("1234567");

        when(storeService.getById(anyString())).thenReturn(dummyStore);


       Product saveProduct =new Product();
        saveProduct.setId("product1");
        saveProduct.setName("Biskuat");
        saveProduct.setDescription("Macan naon? macan biskuat");


        //data ddummy request
        ProductRequest dummyProductRequest = mock(ProductRequest.class);
        when(dummyProductRequest.getStoreId()).thenReturn(StoreResponse.builder()
                .id("store1")
                .build());
        when(dummyProductRequest.getProductName()).thenReturn(saveProduct.getName());
        when(dummyProductRequest.getDescription()).thenReturn(saveProduct.getDescription());
        when(dummyProductRequest.getPrice()).thenReturn(10000L);
        when(dummyProductRequest.getStock()).thenReturn(20);

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(saveProduct);

        //call method create
        ProductResponse productResponse = productService.createProductAndProductPrice(dummyProductRequest);

        //validate response
        assertNotNull(productResponse);
        assertEquals(saveProduct.getName(), productResponse.getNameProduct());

        //vaidate
        assertEquals(dummyProductRequest.getPrice(),productResponse.getPrice());
        assertEquals(dummyProductRequest.getStock(),productResponse.getStock());

        //validate inetraction with store
        assertEquals(dummyProductRequest.getPrice(),productResponse.getPrice());
        assertEquals(dummyProductRequest.getStock(),productResponse.getStock());

        //validate intracton with store
        assertEquals(dummyStore.getId(),productResponse.getStore().getId());

        //verifu interaction with mock object
        verify(storeService).getById(anyString());
        verify(productRepository).saveAndFlush(any(Product.class));
        verify(productPriceService).create(any(ProductPrice.class));
    }
}