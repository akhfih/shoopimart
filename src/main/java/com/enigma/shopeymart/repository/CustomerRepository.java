package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
