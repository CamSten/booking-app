package com.example.bookingapp.service;

import com.example.bookingapp.model.Customer;
import com.example.bookingapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
