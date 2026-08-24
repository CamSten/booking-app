package com.example.bookingapp.controller;

import com.example.bookingapp.model.Booking;
import com.example.bookingapp.model.Room;
import com.example.bookingapp.repository.RoomRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

@Controller
public class FrontendController {

    private final RoomRepository roomRepository;

    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/home";
    }

    public FrontendController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/home")
    public String showHomePage(Model model,
                               @RequestParam(required = false) LocalDate startdate,
                               @RequestParam(required = false) LocalDate enddate) {
        if (startdate != null && enddate != null){
            List<Room> availableRooms = roomRepository.findAvailableRooms(startdate, enddate, Booking.BookingStatus.CANCELLED);
            model.addAttribute("rooms", availableRooms);
        }
        else{
            model.addAttribute("rooms", roomRepository.findAll());
        }
        return "homepage";
    }

    @GetMapping("/room")
    public String showRoomPage(
            @RequestParam Long id,
            @RequestParam(required = false) String startdate,
            @RequestParam(required = false) String enddate,
            Model model) {
        model.addAttribute("room", roomRepository.findById(id).orElse(null));
        model.addAttribute("startdate", startdate);
        model.addAttribute("enddate", enddate);
        return "roompage";
    }

    @GetMapping("/book")
    public String showBookingPage(@RequestParam Long roomId, @RequestParam(required = false) Long bookingId,
            @RequestParam(required = false) LocalDate enddate, @RequestParam(required = false) LocalDate startdate,
            Model model) {

        model.addAttribute("room", roomRepository.findById(roomId).orElse(null));
        model.addAttribute("bookingId", bookingId);
        model.addAttribute("startdate", startdate);
        model.addAttribute("enddate", enddate);

        if (startdate != null && enddate != null){
            return "bookingpage";
        }
        return "bookingpage";
    }

    @GetMapping("/search")
    public String showSearchPage() {
        return "searchpage";
    }

}