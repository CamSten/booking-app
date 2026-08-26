package com.example.customer_service.controller;

import com.example.customer_service.exception.ActiveBookingException;
import com.example.customer_service.exception.EmailExistsException;
import com.example.customer_service.model.Customer;
import com.example.customer_service.model.CustomerDTO;
import com.example.customer_service.model.CustomerResponseDTO;
import com.example.customer_service.model.LoginRequestDTO;
import com.example.customer_service.service.CustomerService;
import com.example.customer_service.service.SessionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final SessionService sessionService;

    public CustomerController(CustomerService customerService, SessionService sessionService) {
        this.customerService = customerService;
        this.sessionService = sessionService;
    }

    @GetMapping("/customer")
    public String showCustomerPage(HttpSession session, Model model) {
//        Long customerId = (Long) session.getAttribute("loginCustomerId");
        Long customerId = sessionService.getCustomerId(session);

        if(customerId != null){
            return "redirect:/profile";
        }

        model.addAttribute("loginCustomer", new Customer());
        model.addAttribute("signupCustomer", new Customer());

        return "customer";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/customer";
    }


    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
//        Long customerId = (Long) session.getAttribute("loginCustomerId");

        Long customerId = sessionService.getCustomerId(session);

        if (customerId == null) {
            return "redirect:/customer";
        }


        Customer customer = customerService.getCustomerById(customerId);

        model.addAttribute("customer", customer);

        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
//        Long customerId = (Long) session.getAttribute("loginCustomerId");
        Long customerId = sessionService.getCustomerId(session);
        if (customerId == null) {
            return "redirect:/customer";
        }

        Customer customer = customerService.getCustomerById(customerId);

        model.addAttribute("customer", customer);

        return "editProfile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("customer") Customer customer, HttpSession session, Model model) {
//        Long customerId = (Long) session.getAttribute("loginCustomerId");

        Long customerId = sessionService.getCustomerId(session);
        if (customerId == null) {
            return "redirect:/customer";
        }

        try {
            customerService.updateCustomer(customerId, customer);

        } catch (EmailExistsException e) {
            model.addAttribute("editError", e.getMessage());

            return "editProfile";
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteCustomer(HttpSession session, RedirectAttributes redirectAttributes) {
//        Long customerId = (Long) session.getAttribute("loginCustomerId");

        Long customerId = sessionService.getCustomerId(session);
        if (customerId == null) {
            return "redirect:/customer";
        }
        try {
            customerService.deleteCustomer(customerId);
            session.invalidate();

            return "redirect:/customer";

        } catch (ActiveBookingException e ) {
            redirectAttributes.addFlashAttribute("deleteError", e.getMessage());

            return "redirect:/profile";
        }
    }

    @GetMapping("/customer/validate")
    public boolean isValidCustomer( @RequestParam Long customerId){
        return customerService.validateCustomer(customerId);
    }

    @GetMapping("/customer/authorize")
    public boolean isAuthorizedCustomer( @RequestParam Long customerId, HttpSession session){
        return customerService.validateAuthorizedCustomer(customerId, (Long) session.getAttribute("loginCustomerId"));
    }
}
