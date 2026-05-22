package com.example.bookingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempThymeleafController {
    @GetMapping("/")
    public String showTempHTML(){
        return "tempHTML";
    }
}
