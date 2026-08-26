package com.example.bookingapp.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SessionService {

    public Long getUserId(HttpSession session) {
        return (session.getAttribute("loginCustomerId") != null) ? (Long) session.getAttribute("loginUserId") : null;
    }
}