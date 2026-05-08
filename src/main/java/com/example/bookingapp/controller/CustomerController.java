package com.example.bookingapp.controller;

import com.example.bookingapp.model.Customer;
import com.example.bookingapp.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/customers")
public class CustomerController {


    private final CustomerService customerService;

    public CustomerController (CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }
}
