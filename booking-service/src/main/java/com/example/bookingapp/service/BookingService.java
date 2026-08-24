
package com.example.bookingapp.service;

import com.example.bookingapp.config.RestTemplateConfig;
import com.example.bookingapp.model.Booking;
import com.example.bookingapp.model.BookingDTO;
import com.example.bookingapp.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    public BookingService(BookingRepository bookingRepository, RestTemplateConfig restTemplateConfig) {
        this.bookingRepository = bookingRepository;
        this.restTemplate = restTemplateConfig.restTemplate();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookingsByCustomerId(Long customerid) {
        List<Booking> bookingList =  bookingRepository.findByCustomerid(customerid);
        return bookingList != null ? bookingList : Collections.emptyList();
    }

    public boolean hasActiveBooking(Long customerId) {
        return bookingRepository.existsByCustomeridAndStatus(customerId, Booking.BookingStatus.ACTIVE);
    }

    public List<Booking> getBookingsByStartdate(LocalDate startdate){
        return bookingRepository.findByStartdate(startdate);
    }
    public List<Booking> getBookingsByRoomId(Long roomId){
        return bookingRepository.findByRoomid(roomId);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public Booking createBooking(BookingDTO req, Long customerId) {
        if (checkRoomAvailability(req.getRoomid(), req.getStartdate(), req.getEnddate(), (long) -1)){
            Booking booking = new Booking();
            booking.setRoomid(req.getRoomid());
            booking.setStartdate(req.getStartdate());
            booking.setEnddate(req.getEnddate());
            booking.setGuestcount(req.getGuestcount());
            booking.setExtrabed(req.isExtrabed());
            booking.setCost(req.getCost());
            booking.setCustomerid(customerId);
            booking.setStatus(Booking.BookingStatus.ACTIVE);
            booking.setSubmitdate(LocalDateTime.now());
            return bookingRepository.save(booking);
        }
        return null;
    }

    public List<Booking> getActiveBookingsByRoomId(Long roomId){
        return bookingRepository.findByRoomidAndStatus(roomId, Booking.BookingStatus.ACTIVE);
    }
    public List<Booking> getActiveBookingsByCustomerId(long customerId){
        return bookingRepository.findByCustomeridAndStatus(customerId, Booking.BookingStatus.ACTIVE)
                .stream().filter(b -> !b.getEnddate().isBefore(LocalDate.now())).toList();
    }

    public boolean checkRoomAvailability(Long roomId, LocalDate startDate, LocalDate enddate, Long bookingId){
        if (!checkDateValidity(startDate, enddate)){
            return false;
        }
        List<Booking> activeBookings = getActiveBookingsByRoomId(roomId);
        for (Booking b : activeBookings){
            boolean dateTaken = (startDate.isBefore(b.getEnddate()) && enddate.isAfter(b.getStartdate()));
            if (dateTaken && !b.getId().equals(bookingId)){
                return false;
            }
        }
        return true;
    }

    public Booking updateBooking(Long bookingId, BookingDTO booking) {
        Booking existingBooking = bookingRepository.findById(bookingId).orElse(null);
        if (existingBooking != null) {
            if (checkRoomAvailability(booking.getRoomid(), booking.getStartdate(), booking.getEnddate(), existingBooking.getId())) {
                existingBooking.setRoomid(booking.getRoomid());
                existingBooking.setGuestcount(booking.getGuestcount());
                existingBooking.setStartdate(booking.getStartdate());
                existingBooking.setEnddate(booking.getEnddate());
                existingBooking.setExtrabed(booking.isExtrabed());
                existingBooking.setCost(booking.getCost());
                return bookingRepository.save(existingBooking);
            }
        }
        return null;
    }

    public Booking cancelBooking(Long id){
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking!= null){
            existingBooking.setStatus(Booking.BookingStatus.CANCELLED);
            return bookingRepository.save(existingBooking);
        }
        return null;
    }

    public boolean checkDateValidity(LocalDate startDate, LocalDate enddate){
        boolean correctOrder = (!startDate.isAfter(enddate) && !startDate.isEqual(enddate));
        boolean notHistoric = (!startDate.isBefore(LocalDate.now()));
        return (correctOrder && notHistoric);
    }

    public boolean isValidCustomerId(Long customerId) {
        return Boolean.TRUE.equals(restTemplate.getForObject("http://localhost:8081/customer/validate?customerId=" + customerId, Boolean.class));
    }

    public boolean isAuthorizedCustomer(Long customerId) {
        return Boolean.TRUE.equals(restTemplate.getForObject("http://localhost:8081/customer/authorize?customerId=" + customerId, Boolean.class));
    }
}
