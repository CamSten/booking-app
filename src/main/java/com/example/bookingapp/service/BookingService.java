package com.example.bookingapp.service;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
    public List<Booking> getBookingsByCustomerId(Long customerid) {
        return bookingRepository.findByCustomerid(customerid);
    }
    public List<Booking> getBookingsByStartdate(LocalDate startdate){
        return bookingRepository.findByStartdate(startdate);
    }
    public List<Booking> getBookingsByRoomId(Long roomId){
        return bookingRepository.findByRoomId(roomId);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public Booking createBooking(Booking book) {
        return bookingRepository.save(book);
    }

    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);

        if (existingBooking != null) {
            existingBooking.setSubmitdate(booking.getSubmitdate());
            existingBooking.setCustomerid(booking.getCustomerid());
            existingBooking.setRoomid(booking.getRoomid());
            existingBooking.setBedcount(booking.getBedcount());
            existingBooking.setNightcount(booking.getNightcount());
            existingBooking.setStartdate(booking.getStartdate());
            existingBooking.setEnddate(booking.getEnddate());
            existingBooking.setCost(booking.getCost());
            return bookingRepository.save(existingBooking);
        } else {
            return null;
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
