package com.example.bookingapp.controller;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.service.BookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        System.out.println("BookingController loaded!");
        this.bookingService = bookingService;
    }

    @GetMapping
    public List<Booking> getBookings() {
        return bookingService.getAllBookings();
    }
}