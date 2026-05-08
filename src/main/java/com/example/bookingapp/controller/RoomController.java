package com.example.bookingapp.controller;

import com.example.bookingapp.model.Customer;
import com.example.bookingapp.model.Room;
import com.example.bookingapp.service.CustomerService;
import com.example.bookingapp.service.RoomService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {


    private final RoomService roomService;

    public RoomController (RoomService roomService){
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getRooms() {
        return roomService.getAllRooms();
    }
}