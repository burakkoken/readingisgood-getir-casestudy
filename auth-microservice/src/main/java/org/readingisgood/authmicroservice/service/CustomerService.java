package org.readingisgood.authmicroservice.service;

import org.readingisgood.authmicroservice.configuration.JwtTokenProvider;
import org.readingisgood.authmicroservice.exception.UserAlreadyRegisteredException;
import org.readingisgood.authmicroservice.model.Customer;
import org.readingisgood.authmicroservice.model.request.LoginRequest;
import org.readingisgood.authmicroservice.model.request.RegisterRequest;
import org.readingisgood.authmicroservice.model.response.LoginResponse;
import org.readingisgood.authmicroservice.model.response.RegisterResponse;
import org.readingisgood.authmicroservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           AuthenticationManager authenticationManager,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public RegisterResponse createNewCustomer(RegisterRequest registerRequest) {
        Optional<Customer> customerOptional = customerRepository.findByUsername(registerRequest.getUsername());
        if(customerOptional.isPresent()) {
            throw new UserAlreadyRegisteredException(registerRequest.getUsername());
        }

        Customer customer = new Customer();
        customer.setUsername(registerRequest.getUsername());
        customer.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));

        customerRepository.save(customer);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setUserId(customer.getId());
        return registerResponse;
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        Optional<Customer> customerOptional = customerRepository.findByUsername(loginRequest.getUsername());
        Customer customer = customerOptional.get();
        String token = jwtTokenProvider.generateToken(customer);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
    }

}
