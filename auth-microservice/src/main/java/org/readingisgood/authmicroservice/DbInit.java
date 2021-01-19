package org.readingisgood.authmicroservice;

import org.readingisgood.authmicroservice.model.Customer;
import org.readingisgood.authmicroservice.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DbInit {

    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public DbInit(CustomerRepository customerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customerRepository = customerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    private void initDatabase() {
        customerRepository.save(new Customer("test1", bCryptPasswordEncoder.encode("123456")));
        customerRepository.save(new Customer("test2", bCryptPasswordEncoder.encode("123456")));
        customerRepository.save(new Customer("test3", bCryptPasswordEncoder.encode("123456")));
    }

}
