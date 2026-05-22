package com.example.bookingapp.model;

import java.time.LocalDate;

public class BookingDTO {
    private Long roomid;
    private int nightCost;
    private LocalDate startdate;
    private LocalDate enddate;
    private int bedcount;
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

    public int getBedcount() {
        return bedcount;
    }

    public void setBedcount(int bedcount) {
        this.bedcount = bedcount;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
    }
}