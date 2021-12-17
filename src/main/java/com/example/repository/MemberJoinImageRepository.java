package com.example.repository;

import com.example.entity.MemberJoinImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJoinImageRepository extends JpaRepository<MemberJoinImage, Long> {
 
        MemberJoinImage findByMember_memberid(String no);

}
