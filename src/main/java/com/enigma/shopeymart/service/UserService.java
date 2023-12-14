package com.enigma.shopeymart.service;

import com.enigma.shopeymart.entity.AppUser;
import com.enigma.shopeymart.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);

}
