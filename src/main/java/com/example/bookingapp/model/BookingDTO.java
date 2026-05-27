package com.example.bookingapp.model;

import java.time.LocalDate;

public class BookingDTO {
    private Long roomid;
    private int cost;
    private LocalDate startdate;
    private LocalDate enddate;
    private int guestcount;
    private boolean extrabed;

    public BookingDTO() {
    }

    public Long getRoomid() {
        return roomid;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public boolean isExtrabed() {
        return extrabed;
    }

    public int getGuestcount() {
        return guestcount;
    }

    public void setGuestcount(int guestcount) {
        this.guestcount = guestcount;
    }

    public void setExtrabed(boolean extrabed) {
        this.extrabed = extrabed;
    }
}