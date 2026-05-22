package com.example.bookingapp.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Booking {
    public enum BookingStatus {
        ACTIVE,
        CANCELLED,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime submitDate;
    private Long customerid;
    private Long roomid;
    private int guestCount;
    private boolean extraBed;
    private LocalDate startDate;
    private LocalDate endDate;
    private int cost;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking() {

    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extrabed) {
        this.extraBed = extrabed;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate enddate) {
        this.endDate = enddate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startdate) {
        this.startDate = startdate;
    }

    public int getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(int bedcount) {
        this.guestCount = bedcount;
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

    public LocalDateTime getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDateTime submitdate) {
        this.submitDate = submitdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}