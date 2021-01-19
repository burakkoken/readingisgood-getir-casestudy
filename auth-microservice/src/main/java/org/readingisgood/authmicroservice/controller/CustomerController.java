package org.readingisgood.authmicroservice.controller;

import org.readingisgood.authmicroservice.model.request.LoginRequest;
import org.readingisgood.authmicroservice.model.request.RegisterRequest;
import org.readingisgood.authmicroservice.model.response.LoginResponse;
import org.readingisgood.authmicroservice.model.response.RegisterResponse;
import org.readingisgood.authmicroservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(customerService.createNewCustomer(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(customerService.authenticateUser(loginRequest));
    }

}
