package com.example.impl;

import com.example.entity.Member;
import com.example.repository.MemberRepository;
import com.example.service.MemberService;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public int InsertMember(Member member) {
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            member.setMemberpw(bcpe.encode(member.getMemberpw()));
            memberRepository.save(member);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int UpdateMember(Member member) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int LoginMember(Member member) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(member.getMemberid(), member.getMemberpw()));
                    String roles = authentication.getAuthorities().iterator().next().toString(); // 권한값추가
                        return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
        }
    }

    @Override
    public int DeleteMember(Member member) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Member SelectMember(Member member) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int IdCheck(Member member) {
        // TODO Auto-generated method stub
        return 0;
    }

}
