
package com.example.bookingapp.service;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class BookingService {
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
        return bookingRepository.findByRoomid(roomId);
    }
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
    public Booking createBooking(Booking booking) {
        if (checkRoomAvailability(booking.getRoomid(), booking.getStartdate(), booking.getEnddate(), (long) -1)){
            booking.setStatus(Booking.BookingStatus.ACTIVE);
            booking.setSubmitdate(LocalDateTime.now());
            return bookingRepository.save(booking);
        }
        return null;
    }

    public List<Booking> getActiveBookingsByRoomId(Long roomId){
        return bookingRepository.findByRoomidAndStatus(roomId, Booking.BookingStatus.ACTIVE);
    }

    public boolean checkRoomAvailability(Long roomId, LocalDate startDate, LocalDate enddate, Long bookingId){
        if (!checkDateValidity(roomId, startDate, enddate)){
            return false;
        }
        List<Booking> activeBookings = getActiveBookingsByRoomId(roomId);
        for (Booking b : activeBookings){
            boolean dateTaken = (startDate.isBefore(b.getEnddate()) &&
                    enddate.isAfter(b.getStartdate()));
            if (dateTaken && !b.getId().equals(bookingId)){
                return false;
            }
        }
        return true;
    }

    public Booking updateBooking(Long id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElse(null);
        if (existingBooking != null) {
            if (checkRoomAvailability(booking.getRoomid(), booking.getStartdate(), booking.getEnddate(), existingBooking.getId())) {
                existingBooking.setSubmitdate(booking.getSubmitdate());
                existingBooking.setCustomerid(booking.getCustomerid());
                existingBooking.setRoomid(booking.getRoomid());
                existingBooking.setBedcount(booking.getBedcount());
                existingBooking.setNightcount(booking.getNightcount());
                existingBooking.setStartdate(booking.getStartdate());
                existingBooking.setEnddate(booking.getEnddate());
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

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public boolean checkDateValidity(Long roomId, LocalDate startDate, LocalDate enddate){
        return  (!startDate.isAfter(enddate) && !startDate.isEqual(enddate));
    }
}
