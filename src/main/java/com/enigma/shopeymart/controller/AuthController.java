package com.enigma.shopeymart.controller;


import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.AUTH)
public class AuthController {

    private  final AuthService authService;
    @PostMapping("/register")
    public RegisterResponse registerCustomer(@RequestBody AuthRequest request){
        return authService.registerCustomer(request);
    }

    @PostMapping("/registeradmin")
    public RegisterResponse registerAdmin(@RequestBody AuthRequest request){
        return authService.registerAdmin(request);
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody AuthRequest request){
     return authService.login(request);
    }

}
