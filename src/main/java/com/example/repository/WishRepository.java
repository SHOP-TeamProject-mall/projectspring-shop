package com.example.repository;

import com.example.entity.MemberWish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<MemberWish, Long> {
    
    MemberWish findByMember_memberid_AndProduct_productno(String memberid, Long productno);

    @Query(value = "SELECT SUM(WISH_COUNT) FROM WISH WHERE MEMBER_ID=:id", nativeQuery = true)
    long queryCountByWishList(@Param("id") String id);

}