package com.example.bookingapp.model;

import com.example.bookingapp.controller.BookingController;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Booking {

    public Booking(){

    }
    @Id
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
