package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StoreServideImplTest {


    //mock storeRepository
    private final StoreRepository storeRepository = mock();

    //crate StoreServicenya instance
    private  final StoreService storeService = new StoreServideImpl(storeRepository);

    @Test
    void itShouldReturnStoreWhenCreateNewStore() {
        Store dummyStore = new Store();
        dummyStore.setId("123");
        dummyStore.setName("Jaya Selalu");

      /*  //mock behavior repository -> save
        when(storeRepository.save(any(Store.class))).thenReturn(dummyStore);

        //perfom then create operation
        Store createStore = storeService.create(dummyStore);

        //verify repository
        verify(storeRepository , times(1)).save(dummyStore);

        //verif / assert
        assertEquals(dummyStore.getId(), createStore.getId());
        assertEquals(dummyStore.getName(), createStore.getName());*/



     StoreRequest dummyStoreRequest = new StoreRequest();
     dummyStoreRequest.setId("123");
     dummyStoreRequest.setName("Jaya Selalu");

     StoreResponse dummyStoreResponse = storeService.create(dummyStoreRequest);

     verify(storeRepository).save(any(Store.class));
     assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());

    }

    @Test
    void itShouldGetAllDataStoreWhenCallGetAll(){
        List<Store> dummyStore = new ArrayList<>();
        dummyStore.add(new Store("1","123","Berkah Selalu","Ragunan","085743553"));
        dummyStore.add(new Store("2","1234","Berkah Selalu Selalu Selalu","Ragunan","085743553"));
        dummyStore.add(new Store("3","12345","Berkah Selalu Selalu Selalu","Ragunan","085743553"));

        when(storeRepository.findAll()).thenReturn(dummyStore);
        List<Store> retriveStore = storeService.getAll();

        assertEquals(dummyStore.size(), retriveStore.size());

        for(int i = 0 ; i < dummyStore.size() ; i++){
            assertEquals(dummyStore.get(i).getName(), retriveStore.get(i).getName());
        }
    }

    @Test
    void itShouldGetDataStoreOneWhenGetByIdStore(){
        String storeId = "1";
        Store store = new Store("1","123","Berkah Selalu","Ragunan","085743553");
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        StoreResponse storeResponse = storeService.getById2(storeId);

        verify(storeRepository).findById(storeId);
        assertNotNull(storeResponse);
        assertEquals(storeId, storeResponse.getId());
        assertEquals("Berkah Selalu", storeResponse.getStoreName());
    }

    @Test
    void deleteById(){
        String id = "1";
        storeService.delete(id);
        verify(storeRepository, times(1)).deleteById(id);
    }

    @Test
    void itShouldReturnStoreWhenUpdateStore(){
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("1");
        dummyStoreRequest.setName("Jaya Selalu");
        dummyStoreRequest.setNoSiup("1234");
        dummyStoreRequest.setAddress("Ngacou");
        dummyStoreRequest.setMobilePhone("234");

        Store exitingStore = new Store("1","12345","Berkah Selalu","Ragunan","085743553");

        when(storeRepository.findById(dummyStoreRequest.getId())).thenReturn(Optional.of(exitingStore));

        StoreResponse storeResponse = storeService.update2(dummyStoreRequest);

        verify(storeRepository, times(1)).findById(dummyStoreRequest.getId());

        verify(storeRepository, times(1)).save(any(Store.class));

        assertNotNull(storeResponse);
        assertEquals(dummyStoreRequest.getId(), storeResponse.getId());
        assertEquals(dummyStoreRequest.getName(), storeResponse.getStoreName());

    }
}