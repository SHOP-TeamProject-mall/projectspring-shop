package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Member;
import com.example.entity.MemberJoinImage;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberJoinImageRepository;
import com.example.repository.MemberRepository;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJoinImageRepository memberJoinImageRepository;

    // 회원가입
    // 127.0.0.1:8080/HOST/member/memberjoin.json
    @PostMapping(value = "/memberjoin.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberJoinPost(@RequestBody Member member) {
        System.out.println(" join -> " +  member.toString());

        // System.out.println("MEMBER:" + member.toString());
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

    // 로그인
    // 127.0.0.1:8080/HOST/member/memberlogin.json
    @PostMapping(value = "/memberlogin.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberLoginPost(@RequestBody Member member) throws IOException {
        System.out.println("++++++++++++" + member.toString());
        // System.out.println("member : ->" + member.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = memberService.LoginMember(member);
            // System.out.println("ret : ->" + ret);
            if (ret == 1) {
                map.put("status", 200); // 로그인 성공 시
                map.put("token", jwtUtil.generateToken(member.getMemberid()));
            } else if (ret == 0) {
                map.put("status", 0); // 권한 불일치 (개인인지 관리자 인지 확인)
            } else if (ret == -1) {
                map.put("status", -1); // DB에 존재하지 않음
            }
        } catch (Exception e) {
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/member/memberlogin.json
    @GetMapping(value = "/memberlogin.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object MemberLoginGet() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 아이디 중복체크
    // 127.0.0.1:8080/HOST/member/memberIdCheck.json?memberid=
    @GetMapping(value = "/memberIdCheck.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberIdCheckGet(@RequestParam("memberid") String memberid) throws IOException {
        // System.out.println("아이디 중복체크 -> memberid:" + memberid);
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = memberService.IdCheck(memberid);
            map.put("status", 200);
            map.put("idCheckchk", ret);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 아이디 찾기
    // 127.0.0.1:8080/HOST/member/memberIdFind.json?membername=
    @GetMapping(value = "/memberIdFind.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberIdFind(@RequestParam("membername") String membername,
            @RequestParam("memberemail") String memberemail) throws IOException {
        // System.out.println("아이디찾기 -> membername:" + membername);
        Map<String, Object> map = new HashMap<>();
        try {
            int ret = memberService.IdFind(membername);
            map.put("status", 200);
            map.put("idfindchk", ret);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 회원가입 시 회원 프로필
    // 127.0.0.1:8080/HOST/member/memberJoinImage.json?no=
    @PostMapping(value = "/memberJoinImage.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> memberJoinImage(@RequestParam(name = "no") String no,
            @RequestParam(name = "file") MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        try {
            Member member = memberRepository.getById(no);
            List<MemberJoinImage> list = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                MemberJoinImage memberJoinImage = new MemberJoinImage();
                memberJoinImage.setMember(member);
                memberJoinImage.setImage(files[i].getBytes());
                memberJoinImage.setImagename(files[i].getOriginalFilename());
                memberJoinImage.setImagesize(files[i].getSize());
                memberJoinImage.setImagetype(files[i].getContentType());
                list.add(memberJoinImage);
            }
            memberJoinImageRepository.saveAll(list);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 555);

        }
        return map;
    }

    // 회원 이미지 가져오기
    // 127.0.0.1:8080/HOST/member/MemberSelect_image?no=1
    @GetMapping(value = "/MemberSelect_image")
    public ResponseEntity<byte[]> MemberSelectImage(@RequestParam("no") String no)
            throws IOException {
        try {
            MemberJoinImage memberJoinImage = memberJoinImageRepository.findByMember_memberid(no);
            if (memberJoinImage.getImage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (memberJoinImage.getImagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (memberJoinImage.getImagetype().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(memberJoinImage.getImage(), headers,
                        HttpStatus.OK);
                return response;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
