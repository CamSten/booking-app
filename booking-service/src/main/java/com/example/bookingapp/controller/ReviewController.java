package com.example.bookingapp.controller;

import com.example.bookingapp.model.ReviewCollectionDTO;
import com.example.bookingapp.model.ReviewRequestDTO;
import com.example.bookingapp.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Void> addReview(@RequestBody ReviewRequestDTO request) {
        return null;
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ReviewCollectionDTO> getReviewsByRoomId(@PathVariable Long roomId) {
        return null;
    }
}
