package com.example.service;

import java.util.List;

import com.example.entity.Member;

import org.springframework.stereotype.Service;

@Service
public interface MemberCartService {
    
    // 카트 리스트 조회
    public List<Member> MemberCartSelect(String memberid);

    // 조회된 장바구니 목록 삭제
    public List<Member> MemberCartDelete(Long membercartno);


}
