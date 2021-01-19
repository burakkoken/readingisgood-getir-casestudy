package org.readingisgood.authmicroservice.service;

import org.readingisgood.authmicroservice.exception.UserNotFoundException;
import org.readingisgood.authmicroservice.model.Customer;
import org.readingisgood.authmicroservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private CustomerRepository customerRepository;

    @Autowired
    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findByUsername(username);
        Customer customer = customerOptional.orElseThrow(() -> new UserNotFoundException(username));
        return new User(customer.getUsername(), customer.getPassword(), emptyList());
    }

}
