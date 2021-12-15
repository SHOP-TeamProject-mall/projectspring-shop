package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRepository;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    // 127.0.0.1:8080/HOST/member/memberjoin.json
    @PostMapping(value = "/memberjoin.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberJoinPost(@RequestBody Member member) {
        System.out.println("MEMBER:" + member.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            member.setMemberrole("USER");
            memberService.InsertMember(member);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/member/memberlogin.json
    @PostMapping(value = "/memberlogin.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberLoginPost(@RequestBody Member member) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = memberService.LoginMember(member);
            if (ret == 1) {
                map.put("status", 200); // 로그인 성공 시
                map.put("token", jwtUtil.generateToken(member.getMemberid()));
            } else if (ret == -1) {
                map.put("status", -1); // DB에 존재하지 않는 회원
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    
}
