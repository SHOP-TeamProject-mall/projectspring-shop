package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.entity.MemberWish;
import com.example.entity.Product;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRepository;
import com.example.repository.ProductRepository;
import com.example.repository.WishRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public class WishController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WishRepository wishRepository;

    // 좋아요(찜) 추가하기
    // 127.0.0.1:8080/HOST/cart/insertcart.json?no=1
    @PutMapping(value = "/insertcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertProductPost(@RequestParam("no") long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        
        try {
            String memberid = jwtUtil.extractUsername(token); // 토큰 회원 아이디 추출
            Member member = memberRepository.getById(memberid); // 회원 아이디
            Product product = productRepository.getById(no); // 상품 코드

            MemberWish memberWish = wishRepository.findByMember_memberid_AndProduct_productno(memberid,
            product.getProductno());
            if (memberWish == null) {
                memberWish = new MemberWish();
                memberWish.setMember(member);
                memberWish.setProduct(product);
                memberWish.setWishcount(1l);
                wishRepository.save(memberWish);
            } else {
                if (memberWish.getWishcount() == 0) {
                    memberWish.setWishcount(1l);
                } else if (memberWish.getWishcount() == 1) {
                    memberWish.setWishcount(0l);
                }
                wishRepository.save(memberWish);
            }
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 좋아요 리스트 조회
    // 127.0.0.1:8080/HOST/cart/selectcart.json
    @GetMapping(value = "/selectcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectcartListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }

    // 찜 회원별 개수 조회
    // 127.0.0.1:8080/HOST/wish/update_wish_hit_select.json
    @GetMapping(value = "/update_wish_hit_select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatewishhit(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            map.put("wishcount", wishRepository.queryCountByWishList(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
