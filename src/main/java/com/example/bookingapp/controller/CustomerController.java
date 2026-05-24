package com.example.bookingapp.controller;

import com.example.bookingapp.model.Customer;
import com.example.bookingapp.repository.CustomerRepository;
import com.example.bookingapp.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    public CustomerController(CustomerService customerService, CustomerRepository customerRepository ) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customer")
    public String showCustomerPage(Model model) {
        model.addAttribute("loginCustomer", new Customer());
        model.addAttribute("signupCustomer", new Customer());
        return "customer";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginCustomer") Customer customer, HttpSession session, Model model) {
        Optional<Customer> loggedinCustomer = customerService.loginCustomer(customer.getEmail(), customer.getPassword());

        if (loggedinCustomer.isPresent()) {
            session.setAttribute("loginCustomerId", loggedinCustomer.get().getId());
            return "redirect:/profile";
        }
        model.addAttribute("error", "Invalid username and/or password");
        return "customer";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signupCustomer") Customer customer, Model model) {
        try{customerService.createCustomer(customer);
        model.addAttribute("success", "Signup Successful");
        return "redirect:/customer";
        } catch(Exception e){
            model.addAttribute("signupError", e.getMessage());
            return "customer";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/customer";
    }

//    @GetMapping("/profile")
//    public String profile(HttpSession session, Model model) {
//        Long customerId = (Long) session.getAttribute("loginCustomerId");
//
//        if (customerId == null) {
//            return "redirect:/customer";
//        }
//
//        Customer customer = customerService.getCustomerById(customerId);
//
//        model.addAttribute("customer", customer);
//
//        return "profile";
//    }

    @GetMapping("/profile")
    public String showProfilePage(@RequestParam Long customerId, Model model) {
        model.addAttribute("customer", customerRepository.findById(customerId).orElse(null));
        return "tempProfileWithBookings";
    }

        Customer customer = customerService.getCustomerById(customerId);

        model.addAttribute("customer", customer);

        return "profile";
    }
}
