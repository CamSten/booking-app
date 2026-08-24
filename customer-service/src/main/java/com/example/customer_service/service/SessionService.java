package com.example.customer_service.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SessionService {

    public Long requireCustomerId(HttpSession session) {
        Long customerId = (Long) session.getAttribute("loginCustomerId");
        if (customerId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required");
        }
        return customerId;
    }

    public Long getCustomerId(HttpSession session) {
        return (session.getAttribute("loginCustomerId") != null) ? (Long) session.getAttribute("loginCustomerId") : null;
    }
}