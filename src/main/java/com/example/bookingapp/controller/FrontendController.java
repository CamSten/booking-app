package com.example.bookingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/home")
    public String showHomePage() {
        return "homepage";
    }

    @GetMapping("/room")
    public String showRoomPage() {
        return "roompage";
    }
}
