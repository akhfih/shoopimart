package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Admin;
import com.enigma.shopeymart.entity.Customer;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public interface AdminService {
    CustomerResponse createNewAdmin(Admin request);
}
