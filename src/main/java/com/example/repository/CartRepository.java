package com.example.repository;

import com.example.entity.MemberCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<MemberCart, Long> {

    MemberCart findByMember_memberid_AndProduct_productno(String memberid, Long productno); // membercart에서 회원아이디랑 상품코드 찾기
    
}
