package com.example.bookingapp.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime submitdate;
    private Long customerid;
    private Long roomid;
    private int bedcount;
    private int nightcount;
    private LocalDate startdate;
    private LocalDate enddate;
    private int cost;

    public Booking() {

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public int getNightcount() {
        return nightcount;
    }

    public void setNightcount(int nightcount) {
        this.nightcount = nightcount;
    }

    public int getBedcount() {
        return bedcount;
    }

    public void setBedcount(int bedcount) {
        this.bedcount = bedcount;
    }

    public Long getRoomid() {
        return roomid;
    }

    public void setRoomid(Long roomid) {
        this.roomid = roomid;
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public LocalDateTime getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(LocalDateTime submitdate) {
        this.submitdate = submitdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}