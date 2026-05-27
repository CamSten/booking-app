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
                singleRoom.setCostPerNight(1000);
                roomRepository.save(singleRoom);
            }

            for (int i = 6; i <= 10; i++) {
                Room doubleRoom = new Room();
                doubleRoom.setRoomNumber("10" + i);
                doubleRoom.setRoomType("Double");
                doubleRoom.setExtraBedAvailable(true);
                doubleRoom.setCostPerNight(1500);
                roomRepository.save(doubleRoom);
            }
        } else {
            java.util.List<Room> existingRooms = roomRepository.findAll();
            for (Room room : existingRooms) {
                if (room.getCostPerNight() == 0) {
                    if ("Single".equals(room.getRoomType())) {
                        room.setCostPerNight(1000);
                        roomRepository.save(room);
                    } else if ("Double".equals(room.getRoomType())) {
                        room.setCostPerNight(1500);
                        roomRepository.save(room);
                    }
                }
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

        if (bookingRepository.count() == 0) {
            Booking b1 = new Booking();
            b1.setCustomerid(5L);
            b1.setRoomid(1L);
            b1.setGuestcount(1);
            b1.setStartdate(LocalDate.of(2026, 6, 1));
            b1.setEnddate(LocalDate.of(2026, 6, 4));
            b1.setCost(3 * 1000);
            b1.setStatus(Booking.BookingStatus.ACTIVE);
            b1.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b1);

            Booking b2 = new Booking();
            b2.setCustomerid(6L);
            b2.setRoomid(2L);
            b2.setGuestcount(1);
            b2.setStartdate(LocalDate.of(2026, 6, 10));
            b2.setEnddate(LocalDate.of(2026, 6, 13));
            b2.setCost(3 * 1000);
            b2.setStatus(Booking.BookingStatus.ACTIVE);
            b2.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b2);

            Booking b3 = new Booking();
            b3.setCustomerid(7L);
            b3.setRoomid(3L);
            b3.setGuestcount(1);
            b3.setStartdate(LocalDate.of(2026, 6, 20));
            b3.setEnddate(LocalDate.of(2026, 6, 23));
            b3.setCost(3 * 1000);
            b3.setStatus(Booking.BookingStatus.ACTIVE);
            b3.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b3);

            Booking b4 = new Booking();
            b4.setCustomerid(8L);
            b4.setRoomid(4L);
            b4.setGuestcount(1);
            b4.setStartdate(LocalDate.of(2026, 6, 5));
            b4.setEnddate(LocalDate.of(2026, 6, 7));
            b4.setCost(2 * 1000);
            b4.setStatus(Booking.BookingStatus.ACTIVE);
            b4.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b4);

            Booking b5a = new Booking();
            b5a.setCustomerid(5L);
            b5a.setRoomid(5L);
            b5a.setGuestcount(1);
            b5a.setStartdate(LocalDate.of(2026, 6, 3));
            b5a.setEnddate(LocalDate.of(2026, 6, 6));
            b5a.setCost(3 * 1000);
            b5a.setStatus(Booking.BookingStatus.ACTIVE);
            b5a.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b5a);

            Booking b5b = new Booking();
            b5b.setCustomerid(6L);
            b5b.setRoomid(5L);
            b5b.setGuestcount(1);
            b5b.setStartdate(LocalDate.of(2026, 6, 12));
            b5b.setEnddate(LocalDate.of(2026, 6, 15));
            b5b.setCost(3 * 1000);
            b5b.setStatus(Booking.BookingStatus.ACTIVE);
            b5b.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b5b);

            Booking b5c = new Booking();
            b5c.setCustomerid(7L);
            b5c.setRoomid(5L);
            b5c.setGuestcount(1);
            b5c.setStartdate(LocalDate.of(2026, 6, 18));
            b5c.setEnddate(LocalDate.of(2026, 6, 21));
            b5c.setCost(3 * 1000);
            b5c.setStatus(Booking.BookingStatus.ACTIVE);
            b5c.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b5c);

            Booking b6 = new Booking();
            b6.setCustomerid(7L);
            b6.setRoomid(6L);
            b6.setGuestcount(3);
            b6.setExtrabed(true);
            b6.setStartdate(LocalDate.of(2026, 6, 8));
            b6.setEnddate(LocalDate.of(2026, 6, 12));
            b6.setCost(4 * (1500 + 250));
            b6.setStatus(Booking.BookingStatus.ACTIVE);
            b6.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b6);

            Booking b7 = new Booking();
            b7.setCustomerid(8L);
            b7.setRoomid(7L);
            b7.setGuestcount(2);
            b7.setExtrabed(false);
            b7.setStartdate(LocalDate.of(2026, 6, 15));
            b7.setEnddate(LocalDate.of(2026, 6, 19));
            b7.setCost(4 * 1500);
            b7.setStatus(Booking.BookingStatus.ACTIVE);
            b7.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b7);

            Booking b8 = new Booking();
            b8.setCustomerid(9L);
            b8.setRoomid(8L);
            b8.setGuestcount(2);
            b8.setExtrabed(false);
            b8.setStartdate(LocalDate.of(2026, 6, 22));
            b8.setEnddate(LocalDate.of(2026, 6, 26));
            b8.setCost(4 * 1500);
            b8.setStatus(Booking.BookingStatus.ACTIVE);
            b8.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b8);

            Booking b9 = new Booking();
            b9.setCustomerid(5L);
            b9.setRoomid(9L);
            b9.setGuestcount(2);
            b9.setExtrabed(false);
            b9.setStartdate(LocalDate.of(2026, 6, 18));
            b9.setEnddate(LocalDate.of(2026, 6, 20));
            b9.setCost(2 * 1500);
            b9.setStatus(Booking.BookingStatus.ACTIVE);
            b9.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b9);

            Booking b10 = new Booking();
            b10.setCustomerid(6L);
            b10.setRoomid(10L);
            b10.setGuestcount(2);
            b10.setExtrabed(false);
            b10.setStartdate(LocalDate.of(2026, 6, 25));
            b10.setEnddate(LocalDate.of(2026, 6, 29));
            b10.setCost(4 * 1500);
            b10.setStatus(Booking.BookingStatus.ACTIVE);
            b10.setSubmitdate(LocalDateTime.now());
            bookingRepository.save(b10);
        }
    }
}