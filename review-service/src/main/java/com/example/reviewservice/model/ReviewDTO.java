package com.example.reviewservice.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
public class ReviewDTO {
    private int stars;
    private String comments;
    private String customer;
    private LocalDate startdate;
    private LocalDate enddate;
    private LocalDate submitdate;

    public ReviewDTO() {
    }
}
