package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;

import java.util.List;

public interface CustomerService {


    CustomerResponse create(CustomerRequest customerRequest);

    Customer createNewCustomer(Customer request);

    CustomerResponse createNewCustomer2(Customer request);
    CustomerResponse getById(String id);

    List<CustomerResponse> getAll();
    
    CustomerResponse update(CustomerRequest customerRequest);

    void delete (String id);

}
