package com.example.bookingapp.model;

import java.time.LocalDate;

public class BookingDTO {
    private Long roomid;
    private int nightcost;
    private LocalDate startdate;
    private LocalDate enddate;
    private int guestcount;
    private boolean extrabed;

    public BookingDTO() {
    }

    public Long getRoomid() {
        return roomid;
    }

    public int getNightcost() {
        return nightcost;
    }
    public void setNightcost(int nightcost) {
        this.nightcost = nightcost;
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