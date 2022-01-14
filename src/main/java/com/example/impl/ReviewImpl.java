package com.example.impl;

import java.util.List;

import com.example.entity.Review;
import com.example.repository.ReviewRepository;
import com.example.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewImpl implements ReviewService{

    @Autowired
    ReviewRepository repository;

    @Override
    public Review InsertReview(Review review) {
        try{
            return repository.save(review);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Review> SelectReview() {
        
        repository.findAll();
        return null;
    }
    
}
