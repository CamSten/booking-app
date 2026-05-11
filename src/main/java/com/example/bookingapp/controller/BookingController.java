package com.example.bookingapp.controller;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public List<Booking> getAllBooks() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }
    @GetMapping("/customer/{customerid}")
    public List<Booking> getBookingsByCustomerId(@PathVariable Long customerid) {
        return bookingService.getBookingsByCustomerId(customerid);
    }
    @GetMapping("/room/{roomid}")
    public List<Booking> getBookingsByRoomId(@PathVariable Long roomid) {
        return bookingService.getBookingsByRoomId(roomid);
    }
    @GetMapping("/startdate/{date}")
    public List<Booking> getBookingsByStartdate(@PathVariable LocalDate date) {
        return bookingService.getBookingsByStartdate(date);
    }

    @PostMapping("")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}