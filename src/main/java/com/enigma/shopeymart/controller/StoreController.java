package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.STORE)
public class StoreController {
    private final StoreService storeService;

    //create
//    @PostMapping(value="/store")
    @PostMapping
    public Store createStore(@RequestBody Store store) {
        return storeService.create(store);
    }

    //getAll
//    @GetMapping(value = "/store")
    @GetMapping
    public List<Store> getAllStore(){
        return  storeService.getAll();
    }

    //getById
    @GetMapping(value = "/{id}")
    public Store getByIdStore(@PathVariable String id){
        return  storeService.getById(id);
    }

    //delete
    @DeleteMapping(value = "/{id}")
    public void deleteStore(@PathVariable String id){
        storeService.delete(id);


    }
    //update'
    @PutMapping(value = "/{id}")
    public Store updateStore(@PathVariable String id, @RequestBody Store store){
        store.setId(id);
        return storeService.update(store);
    }

    @PostMapping(value="/v2")
    public StoreResponse createStores(@RequestBody StoreRequest storeRequest){
        return  storeService.create(storeRequest);
    }




}
