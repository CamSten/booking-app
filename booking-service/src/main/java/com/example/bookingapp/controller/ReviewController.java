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
        reviewService.saveReview(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ReviewCollectionDTO> getReviewsByRoomId(@PathVariable Long roomId) {
        ReviewCollectionDTO collection = reviewService.getReviewsForRoom(roomId);
        return ResponseEntity.ok(collection);
    }
}
