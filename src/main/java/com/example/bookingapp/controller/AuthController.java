package com.example.bookingapp.controller;

import com.example.bookingapp.model.Customer;
import com.example.bookingapp.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class AuthController {

    private final CustomerService customerService;
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/auth")
    public String showAuthPage(Model model) {
        model.addAttribute("loginCustomer", new Customer());
        model.addAttribute("signupCustomer", new Customer());
        return "auth";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginCustomer") Customer customer, HttpSession session, Model model) {
        Optional<Customer> loggedinCustomer = customerService.loginCustomer(customer.getEmail(), customer.getPassword());

        if (loggedinCustomer.isPresent()) {
            session.setAttribute("loginCustomerId", loggedinCustomer.get().getId());
            return "redirect:/profile";
        }
        model.addAttribute("error", "Invalid username and/or password");
        return "auth";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("signupCustomer") Customer customer, Model model) {
        customerService.createCustomer(customer);
        model.addAttribute("success", "Signup Successful");
        return "redirect:/auth";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");

        if (customerId == null) {
            return "redirect:/auth";
        }

        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            return "redirect:/auth";
        }

        model.addAttribute("customer", customer);

        return "profile";
    }
}
