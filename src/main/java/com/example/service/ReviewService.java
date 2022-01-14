package com.example.service;

import java.util.List;

import com.example.entity.Review;

import org.springframework.stereotype.Service;

@Service
public interface ReviewService {
    public Review InsertReview(Review review);

    public List<Review> SelectReview();
}
