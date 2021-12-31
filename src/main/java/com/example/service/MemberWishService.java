package com.example.service;

import java.util.List;

import com.example.entity.Member;

import org.springframework.stereotype.Service;

@Service
public interface MemberWishService {

    // 위시 리스트 조회
    public List<Member> MemberWishSelect(String memberid);

    // 조회된 위시리스트 목록 삭제
    public int MemberWishDelete(Long memberwish);
    
}
