package com.example.bookingapp;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.model.Customer;
import com.example.bookingapp.model.Room;
import com.example.bookingapp.repository.BookingRepository;
import com.example.bookingapp.repository.CustomerRepository;
import com.example.bookingapp.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    public InitialDataLoader(RoomRepository roomRepository, BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roomRepository.count() == 0) {
            for (int i = 1; i <= 5; i++) {
                Room singleRoom = new Room();
                singleRoom.setRoomNumber("10" + i);
                singleRoom.setRoomType("Single");
                singleRoom.setExtraBedAvailable(false);
                roomRepository.save(singleRoom);
            }

            for (int i = 6; i <= 10; i++) {
                Room doubleRoom = new Room();
                doubleRoom.setRoomNumber("10" + i);
                doubleRoom.setRoomType("Double");
                doubleRoom.setExtraBedAvailable(true);
                roomRepository.save(doubleRoom);
            }
        }
        if (customerRepository.count() == 0) {
            Customer c = new Customer();
            c.setName("test");
            c.setEmail("test@mail.com");
            c.setAddress("teststreet");
            c.setPhone("123456");
            c.setPassword("$2a$10$5Xx1MUY0nyeww/5/E47KZ.W7hQ0JRBUqjmqF5LgralL6.iupjHgBm");

            customerRepository.save(c);
        }
//        bookingRepository.deleteAll();
        if (bookingRepository.count() == 0) {
            Booking b1 = new Booking();
            b1.setCustomerid(5L);
            b1.setRoomid(1L);
            b1.setGuestcount(2);
            b1.setStartdate(LocalDate.now().plusDays(2));
            b1.setEnddate(LocalDate.now().plusDays(5));
            b1.setCost(3 * 1200);
            b1.setStatus(Booking.BookingStatus.ACTIVE);
            b1.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b1);

            Booking b2 = new Booking();
            b2.setCustomerid(5L);
            b2.setRoomid(7L);
            b2.setGuestcount(2);
            b2.setStartdate(LocalDate.now().plusDays(7));
            b2.setEnddate(LocalDate.now().plusDays(12));
            b2.setCost(5 * 1200);
            b2.setStatus(Booking.BookingStatus.ACTIVE);
            b2.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b2);
        }
    }
}

