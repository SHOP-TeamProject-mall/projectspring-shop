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
            if (member.getMemberrole() == "USER") {
                if (roles.equals("USER")) {
                    return 1; // 타입일치시 1
                }
            } else if (member.getMemberrole() == "ADMIN") {
                if (roles.equals("ADMIN")) {
                    return 1;
                }
            }
            return 0; // 권한 불일치 시 0
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
    public int IdCheck(String memberid) {
        try {
            return sqlSessionFactory.openSession().selectOne("Member.IdCheck", memberid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int IdFind(String memberid) {
        try {
            return sqlSessionFactory.openSession().selectOne("Member.IdFind", memberid);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}
