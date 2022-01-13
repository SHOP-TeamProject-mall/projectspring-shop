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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
        System.out.println(" join -> " + member.toString());

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
        // System.out.println("++++++++++++" + member.toString());
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
            map.put("idCheckchk", ret); // 아이디가 중복이면 ret = 1
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
            @RequestParam(name = "file", required = false) MultipartFile files) throws IOException {

        // file param이 files 데이터가 안올 경우 required를 이용해서 null 값 표현 (즉, 사용자가 이미지를 첨부안했을 경우)
        System.out.println(" 회원가입시 프로필 files : => " + files);

        Map<String, Object> map = new HashMap<>();
        try {
            Member member = memberRepository.getById(no);
            if (files != null) {
                List<MemberJoinImage> list = new ArrayList<>();
                MemberJoinImage memberJoinImage = new MemberJoinImage();
                memberJoinImage.setMember(member);
                memberJoinImage.setImage(files.getBytes());
                memberJoinImage.setImagename(files.getOriginalFilename());
                memberJoinImage.setImagesize(files.getSize());
                memberJoinImage.setImagetype(files.getContentType());
                list.add(memberJoinImage);
                memberJoinImageRepository.saveAll(list);
            } else { // files 가 null 일 때 이미지 null 데이터를 삽입
                
                // List<MemberJoinImage> list = new ArrayList<>();
                MemberJoinImage memberJoinImage = new MemberJoinImage();
                memberJoinImage.setMember(member);
                memberJoinImage.setImage(null); // byte
                memberJoinImage.setImagename(""); // String
                memberJoinImage.setImagesize(0L); // long
                memberJoinImage.setImagetype(""); // String
                // list.add(memberJoinImage);
                memberJoinImageRepository.save(memberJoinImage);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());

        }
        return map;
    }

    //  마이페이지 회원 이미지 수정
    // 127.0.0.1:8080/HOST/member/memberiamgeupdate.json?memberid
    @PostMapping(value = "/memberiamgeupdate.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> UpdateMemberImage(@RequestParam(name = "memberid") String memberid,
            @RequestParam(name = "updatefile") MultipartFile files) throws IOException {
        // System.out.println("회원 마이페이지 이미지 수정 => " + files);
        Map<String, Object> map = new HashMap<>();
        try {
            if (files != null) {
                MemberJoinImage memberJoinImage = memberJoinImageRepository.findByMember_memberid(memberid);
                memberJoinImage.setImage(files.getBytes());
                memberJoinImage.setImagename(files.getOriginalFilename());
                memberJoinImage.setImagesize(files.getSize());
                memberJoinImage.setImagetype(files.getContentType());
                memberJoinImageRepository.save(memberJoinImage);
            }
            map.put("sucess", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 회원 이미지 가져오기
    // 127.0.0.1:8080/HOST/member/MemberSelect_image?no=1
    @GetMapping(value = "/MemberSelect_image")
    public ResponseEntity<byte[]> MemberSelectImage(@RequestParam("no") String no)
            throws IOException {
        try {
            // String userid = jwtUtil.extractUsername(token);
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

    // 회원 정보 수정
    // 127.0.0.1:8080/HOST/member/memberupdate.json
    @PutMapping(value = "/memberupdate.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberUpdatePost(@RequestBody Member member, @RequestHeader("TOKEN") String token) {
        // System.out.println("회원정보수정 -> " + member.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Member member2 = memberService.SelectMember(userid); // 기존 회원정보 꺼내기
            // System.out.println("기존 회원 정보 -> " +member2.toString());
            // member2.setMemberid(member.getMemberid());
            member2.setMembername(member.getMembername());
            member2.setMemberphone(member.getMemberphone());
            member2.setMemberaddress(member.getMemberaddress());
            member2.setMemberemail(member.getMemberemail());
            memberService.UpdateMember(member2);
            map.put("success", 200);
        } catch (Exception e) {
            map.put("fail", e.hashCode());
        }
        return map;
    }

    // 회원 정보 가져오기
    // 127.0.0.1:8080/HOST/member/memberlist.json
    @GetMapping(value = "/memberlist.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object MemberListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            map.put("memberlist", memberService.SelectMember(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 회원 비밀번호 변경
    // 127.0.0.1:8080/HOST/member/memberpwupdate.json
    @PostMapping(value = "/memberpwupdate.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatepw(@RequestBody Member member, @RequestHeader("TOKEN") String token) {
        // System.out.println("암호변경 회원정보" + member.toString());
        // System.out.println("암호변경 토큰 " + token);
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Member member2 = memberService.SelectMember(userid); // 원래 회원정보 꺼내기
            System.out.println("암호변경 회원정보2 : => " + member2.toString());

            BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
            String newpw = bcpe.encode(member2.getMemberpw()); // 새 비밀번호
            String oldpw = member2.getMemberpw(); // 원래비밀번호
            System.out.println("기존 비밀번호 : => " + oldpw);
            System.out.println("새 비밀번호   : => " + newpw);

            if (bcpe.matches(member2.getMemberpw(), oldpw)) { // 원래비밀번호 확인후 새비밀번호
                Member member3 = new Member();
                member3.setMemberpw(newpw); // 새비밀번호
                memberRepository.save(member3);
            }
            map.put("success", 200);
        } catch (Exception e) {
            map.put("fail", e.hashCode());
        }
        return map;
    }

    // 회원 비밀번호 비교
    // 127.0.0.1:8080/HOST/member/memberpwContrast.json
    @GetMapping(value = "/memberpwContrast.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberpwContrast(@RequestBody Member member, @RequestHeader("TOKEN") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            Member member2 = memberService.SelectMember(userid); // 원래 회원정보 꺼내기
            int ret = memberService.MatchPwCheck(member2);
            map.put("status", 200);
            map.put("ret", ret);
        } catch (Exception e) {
            map.put("fail", e.hashCode());
        }
        return map;
    }

}
