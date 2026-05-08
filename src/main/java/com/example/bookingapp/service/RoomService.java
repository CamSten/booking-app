package com.example.bookingapp.service;
import com.example.bookingapp.model.Room;
import com.example.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {


    private final RoomRepository roomRepository;

    public RoomService (RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
