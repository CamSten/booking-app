package com.example.bookingapp.model;

import java.time.LocalDate;

public class BookingDTO {
    private Long roomid;
    private int nightCost;
    private LocalDate startDate;
    private LocalDate endDate;
    private int guestCount;
    private boolean extraBed;

    public BookingDTO() {
    }

    public Long getRoomid() {
        return roomid;
    }

    public int getNightCost() {
        return nightCost;
    }
    public void setNightCost(int nightCost) {
        this.nightCost = nightCost;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
    }
}