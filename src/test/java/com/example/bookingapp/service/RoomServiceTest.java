package com.example.bookingapp.service;

import com.example.bookingapp.model.Room;
import com.example.bookingapp.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRoomsReturnsListOfRooms() {
        Room room = new Room();
        when(roomRepository.findAll()).thenReturn(List.of(room));

        List<Room> rooms = roomService.getAllRooms();

        assertEquals(1, rooms.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void getRoomByIdReturnsRoomWhenExists() {
        Room room = new Room();
        room.setRoomNumber("101");
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Room foundRoom = roomService.getRoomById(1L);

        assertEquals("101", foundRoom.getRoomNumber());
        verify(roomRepository, times(1)).findById(1L);
    }
}
