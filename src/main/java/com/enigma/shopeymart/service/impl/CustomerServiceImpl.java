package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.repository.CustomerRepository;
import com.enigma.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .mobilePhone(customerRequest.getMobilePhone())
                .email(customerRequest.getMobilePhone())
                .build();
        Customer customerSave = customerRepository.save(customer);
        return CustomerResponse.builder()
                .name(customerSave.getName())
                .address(customerSave.getAddress())
                .mobilePhone(customerSave.getMobilePhone())
                .email(customerSave.getEmail())
                .build();
    }



    @Override
    public CustomerResponse getById(String id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        assert customer != null;
        return CustomerResponse.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .mobilePhone(customer.getMobilePhone())
                .email(customer.getEmail())
                .build();
        }

    @Override
    public List<CustomerResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();

        return  customers.stream().map(customer -> CustomerResponse.builder()
                .name(customer.getName())
                .address(customer.getAddress())
                .mobilePhone(customer.getMobilePhone())
                .email(customer.getEmail())
                .build()).collect(Collectors.toList());

    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        CustomerResponse currentCustomerId = getById(customerRequest.getId());
        if(currentCustomerId!=null){
            Customer customer = Customer.builder()
                    .name(customerRequest.getName())
                    .address(customerRequest.getAddress())
                    .mobilePhone(customerRequest.getMobilePhone())
                    .email(customerRequest.getMobilePhone())
                    .build();
            customerRepository.save(customer);
            return CustomerResponse.builder()
                    .name(customer.getName())
                    .address(customer.getAddress())
                    .mobilePhone(customer.getMobilePhone())
                    .email(customer.getEmail())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        customerRepository.deleteById(id);
    }
}
