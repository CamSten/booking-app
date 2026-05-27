package com.example.bookingapp.controller;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.model.BookingDTO;
import com.example.bookingapp.model.Room;
import com.example.bookingapp.service.BookingService;
import com.example.bookingapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomService roomService;

    @GetMapping("")
    public List<Booking> getAllBookings() {
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
    public boolean checkRoomAvailability(
            @PathVariable Long roomid,
            @PathVariable LocalDate startdate,
            @PathVariable LocalDate enddate) {
        return bookingService.checkRoomAvailability(roomid, startdate, enddate, (long) -1);
    }
    @GetMapping("/availability/{startdate}/{enddate}")
    public List<Room> getAvailableRoomsByTimeframe(
        @PathVariable LocalDate startdate,
        @PathVariable LocalDate enddate){
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
    public Booking createBooking(@RequestBody BookingDTO booking, jakarta.servlet.http.HttpSession session) {
        Long customerId = (Long) session.getAttribute("loginCustomerId");
        if (customerId == null) {
            throw new RuntimeException("User must be logged in to book a room");
        }
        return bookingService.createBooking(booking, customerId);
    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody BookingDTO booking) {
        return bookingService.updateBooking(id, booking);
    }
    @PutMapping("/{id}/cancel")
    public Booking cancelBooking (@PathVariable Long id){
        return bookingService.cancelBooking(id);
    }
}