package com.example.bookingapp;

import com.example.bookingapp.model.Room;
import com.example.bookingapp.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public InitialDataLoader(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
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
    }
}
