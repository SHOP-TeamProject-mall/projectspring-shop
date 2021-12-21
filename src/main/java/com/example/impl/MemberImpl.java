package com.example.impl;

import java.util.Optional;

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

    @Autowired
    MemberService memberService;

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
        memberRepository.save(member);
        return 0;
    }

    @Override
    public int LoginMember(Member member) { // equals 명령어 (문자열 비교)
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(member.getMemberid(), member.getMemberpw()));

            System.out.println("authentication : => " + authentication);
            String roles = authentication.getAuthorities().iterator().next().toString(); // 권한값추가
            // System.out.println(" roles : -> " + roles);
            // System.out.println(" member.getMemberrole : -> " + member.getMemberrole());
            // System.out.println(" member.getMemberid : -> " + member.getMemberid());
            // System.out.println(" member.getMemberid : -> " + member.getMemberid());
            if (roles.equals("USER")) {
                return 1;
            } else {
                return 0;
            }
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
    public Member SelectMember(String id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.orElse(null); // 없으면 null 리턴
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

    @Override
    public int MatchPwCheck(Member member) {
        try {
            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            String rawPw = member.getMemberpw(); // 원래 비밀번호
            String encodedPw = bcpe.encode(member.getMemberpw()); // 새 비밀번호
            // System.out.println("원래비밀번호 : => " + rawPw);
            // System.out.println("새비밀번호 : => " + encodedPw);
            if (bcpe.matches(rawPw, encodedPw)) { // 원래 비밀번호와 새 비밀번호 비교
                if (rawPw != encodedPw) {
                    member.setMemberpw(encodedPw);
                    return 1;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int PwCheck(String memberpw) {
        // TODO Auto-generated method stub
        return 0;
    }
}
