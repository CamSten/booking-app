package com.example.bookingapp.controller;

import com.example.bookingapp.model.*;
import com.example.bookingapp.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;
    private final RoomService roomService;

    public BookingController (BookingService bookingService, RoomService roomService){
        this.bookingService = bookingService;
        this.roomService = roomService;
    }
//Changed from "/".
    @GetMapping("/get-all-bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @GetMapping("/customer/{customerId}/has-active-booking")
    public boolean hasActiveBooking(@PathVariable Long customerId) {
        return bookingService.hasActiveBooking(customerId);
    }

    @GetMapping("/customer/{customerid}")
    public List<Booking> getBookingsByCustomerId(@PathVariable Long customerid) {
        return bookingService.getBookingsByCustomerId(customerid);
    }

    @GetMapping("/customer/active/{customerid}")
    public List<Booking> getUpcomingBookingsByCustomerId(@PathVariable Long customerid) {
        return bookingService.getActiveBookingsByCustomerId(customerid);
    }

    @GetMapping("/room/{roomid}")
    public List<Booking> getBookingsByRoomId(@PathVariable Long roomid) {
        return bookingService.getBookingsByRoomId(roomid);
    }

    @GetMapping("/startdate/{date}")
    public List<Booking> getBookingsByStartdate(@PathVariable LocalDate date) {
        return bookingService.getBookingsByStartdate(date);
    }

    @GetMapping("/availability/{roomid}/{startdate}/{enddate}")
    public boolean checkRoomAvailability(@PathVariable Long roomid, @PathVariable LocalDate startdate, @PathVariable LocalDate enddate) {
        return bookingService.checkRoomAvailability(roomid, startdate, enddate, (long) -1);
    }

    @GetMapping("/availability/{startdate}/{enddate}")
    public List<Room> getAvailableRoomsByTimeframe(@PathVariable LocalDate startdate, @PathVariable LocalDate enddate){
        List<Room> allRooms = roomService.getAllRooms();
        List<Room> availableRooms = new ArrayList<>();
        for (Room r : allRooms){
            if(checkRoomAvailability(r.getId(), startdate, enddate)){
                availableRooms.add(r);
            }
        }
        return availableRooms;
    }

    @GetMapping("/room/{roomid}/blocked-dates")
    public List<Booking> getBlockedDates(@PathVariable Long roomid) {
        return bookingService.getActiveBookingsByRoomId(roomid);
    }

    @PostMapping("")
    public Booking createBooking(@RequestBody BookingDTO booking, @RequestParam Long customerId) {
        if (!bookingService.isAuthorizedCustomer(customerId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid customer");
        }
        return bookingService.createBooking(booking, customerId);
    }

//    @PostMapping("")
//    public Booking createBooking(@RequestBody BookingDTO booking, @RequestParam Long customerId) {
//        if (!bookingService.isAuthorizedCustomer(customerId)) {
//            throw new RuntimeException("User must be logged in to book a room");
//            return null;
//        }
//        return bookingService.createBooking(booking, customerId);
//    }

    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable Long bookingId, @RequestBody BookingDTO booking, @RequestParam Long customerId) {
        if (!bookingService.isAuthorizedCustomer(customerId)) {
//            throw new RuntimeException("User must be logged in to update a booking");
            return null;
        }
        return bookingService.updateBooking(bookingId, booking);
    }

    @PutMapping("/{bookingId}/cancel")
    public Booking cancelBooking (@PathVariable Long bookingId, @RequestParam Long customerId){
        if (!bookingService.isAuthorizedCustomer(customerId)) {
//            throw new RuntimeException("User must be logged in to cancel a booking");
            return null;
        }
        return bookingService.cancelBooking(bookingId);
    }
}