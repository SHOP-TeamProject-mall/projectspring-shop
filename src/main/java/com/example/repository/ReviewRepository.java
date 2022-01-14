package com.example.repository;

import java.util.List;

import com.example.entity.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{

    List<Review> findAllByReviewnoOrderByReviewnoDesc(Long no);

    Page<Review> findAll(Pageable pageable);

    // long countByReviewContaining(Review review);
}
