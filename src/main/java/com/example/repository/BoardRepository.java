package com.example.repository;

import com.example.entity.MemberBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<MemberBoard, Long> {
    
}
