package com.enigma.shopeymart.service.impl;


import com.enigma.shopeymart.constant.ERole;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.Role;
import com.enigma.shopeymart.entity.UserCredential;
import com.enigma.shopeymart.repository.UserCredentialRepository;
import com.enigma.shopeymart.service.AuthService;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            //TODO 2 : set role
            Role role = Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            role =   roleService.getOrSave(role);

            //TODO 1 : set credential
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);


            //TODO 3 set customer
            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .name(request.getCustomerName())
                    .address(request.getAddress())
                    .email(request.getEmail())
                    .mobilePhone(request.getMobilePhone())
                    .build();

            customerService.createNewCustomer2(customer);

            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();


        }catch (DataIntegrityViolationException e){
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }
}
