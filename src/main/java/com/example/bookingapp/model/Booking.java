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
    private LocalDateTime submitdate;
    private Long customerid;
    private Long roomid;
    private int guestcount;
    private boolean extrabed;
    private LocalDate startdate;
    private LocalDate enddate;
    private int cost;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Booking() {

    }

    public int getGuestcount() {
        return guestcount;
    }

    public void setGuestcount(int guest_count) {
        this.guestcount = guest_count;
    }

    public boolean isExtrabed() {
        return extrabed;
    }

    public void setExtrabed(boolean extrabed) {
        this.extrabed = extrabed;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
}