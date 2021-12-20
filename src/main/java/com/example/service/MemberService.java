package com.example.service;

import com.example.entity.Member;

import org.springframework.stereotype.Service;

@Service
public interface MemberService {

    // 회원가입 정보 추가
    public int InsertMember(Member member);

    // 회원 정보수정
    public int UpdateMember(Member member);

    // 회원 로그인
    public int LoginMember(Member member);

    // 회원탈퇴
    public int DeleteMember(Member member);

    // 회원 정보 가져오기
    public Member SelectMember(String id);

    // 회원 아이디 중복체크
    public int IdCheck(String memberid);

    // 회원 아이디 찾기 (일단 이름)
    public int IdFind(String memberid);

}
