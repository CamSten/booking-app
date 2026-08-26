//package com.example.bookingapp.controller;
//
//import com.example.customer_service.exception.ActiveBookingException;
//import com.example.customer_service.exception.EmailExistsException;
//import com.example.customer_service.model.Customer;
//import com.example.customer_service.service.CustomerService;
//import com.example.customer_service.service.SessionService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.Optional;
//
//
//@Controller
//public class CustomerController {
//    private final CustomerService customerService;
//    private final SessionService sessionService;
//
//    public CustomerController(CustomerService customerService, SessionService sessionService) {
//        this.customerService = customerService;
//        this.sessionService = sessionService;
//    }
//
//
//
//    @GetMapping("/customer/validate")
//    public boolean isValidCustomer( @RequestParam Long customerId){
//        return customerService.validateCustomer(customerId);
//    }
//
//    @GetMapping("/customer/authorize")
//    public boolean isAuthorizedCustomer( @RequestParam Long customerId, HttpSession session){
//        return customerService.validateAuthorizedCustomer(customerId, (Long) session.getAttribute("loginCustomerId"));
//    }
//}
