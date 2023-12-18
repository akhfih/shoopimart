package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor //inject
public class StoreServideImpl implements StoreService {

    private final StoreRepository storeRepository;


    @Override
    public Store create(Store store) { // inject
        return storeRepository.save(store);
    }

    @Override
    public Store getById(String id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Store update(Store store) {
        Store currentStoreId = getById(store.getId());
        if (currentStoreId != null) {
            return storeRepository.save(store);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        storeRepository.deleteById(id);
    }

    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);
        return StoreResponse.builder()
                .storeName(store.getName())
                .noSiup(storeRequest.getNoSiup())
                .build();
    }

    @Override
    public StoreResponse getById2(String id) {
        Store store = storeRepository.findById(id).orElse(null);

        return StoreResponse.builder()
                .id(store.getId())
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .address(store.getAddress())
                .phone(store.getMobilePhone())
                .build();
    }

    @Override
    public StoreResponse update2(StoreRequest storeRequest) {
        Store currentStoreId =  storeRepository.findById(storeRequest.getId()).orElse(null);
        if (currentStoreId != null) {
            Store store = Store.builder()
                    .id(storeRequest.getId())
                    .name(storeRequest.getName())
                    .address(storeRequest.getAddress())
                    .noSiup(storeRequest.getNoSiup())
                    .mobilePhone(storeRequest.getMobilePhone())
                    .build();
            storeRepository.save(store);
            return StoreResponse.builder()
                    .id(store.getId())
                    .storeName(store.getName())
                    .address(store.getAddress())
                    .noSiup(store.getNoSiup())
                    .phone(store.getMobilePhone())
                    .build();
        }
        return null;

    }
}
