package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.entity.MemberWish;
import com.example.entity.Product;
import com.example.entity.ProductMainImage;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRepository;
import com.example.repository.ProductMainImageRepository;
import com.example.repository.ProductRepository;
import com.example.repository.WishRepository;
import com.example.service.MemberService;
import com.example.service.MemberWishService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/wish")
@RestController
public class WishController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WishRepository wishRepository;

    @Autowired
    MemberWishService memberWishService;

    @Autowired
    ProductMainImageRepository productMainImageRepository;

    // 좋아요(찜) 추가하기
    // 127.0.0.1:8080/HOST/wish/insertwish.json?no=
    @PostMapping(value = "/insertwish.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertProductPost(@RequestParam("productno") long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        

        System.out.println("productno : =>" + token);
        System.out.println("productno    : =>" + no);

        try {
            String memberid = jwtUtil.extractUsername(token); // 토큰 회원 아이디 추출
            Member member = memberRepository.getById(memberid); // 회원 아이디
            Product product = productRepository.getById(no); // 상품 코드
            // System.out.println(member);
            // System.out.println(product);

            MemberWish memberWish = wishRepository.findByMember_memberid_AndProduct_productno(memberid,
                    product.getProductno());
            if (memberWish == null) {
                memberWish = new MemberWish();
                memberWish.setMember(member);
                memberWish.setProduct(product);
                memberWish.setWishcount(1L);
                wishRepository.save(memberWish);
            } else {
                if (memberWish.getWishcount() == 0) {
                    memberWish.setWishcount(1L);
                } else if (memberWish.getWishcount() == 1) {
                    memberWish.setWishcount(0L);
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
    // 127.0.0.1:8080/HOST/wish/selectwish.json
    @GetMapping(value = "/selectwish.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectwishListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String memberid = jwtUtil.extractUsername(token);
            map.put("wishlist", memberWishService.MemberWishSelect(memberid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 찜 회원별 개수 조회
    // 127.0.0.1:8080/HOST/wish/wish_hit_select.json
    @GetMapping(value = "/wish_hit_select.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatewishhit(@RequestParam("no") long productno) {
        Map<String, Object> map = new HashMap<>();
        try {
            // String userid = jwtUtil.extractUsername(token);
            // map.put("memberwish", wishRepository.queryCountByWishList(userid,
            // productno));
            // map.put("memberwish", wishRepository.queryCountByWishList(productno));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 위시리스트 이미지 조회
    // 127.0.0.1:8080/HOST/wish/wishselect_image?no=번호
    @RequestMapping(value = "/wishselect_image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> selectImage1(@RequestParam("no") long no) throws IOException {
        // Map<String, Object> map = new HashMap<>();
        try {
            ProductMainImage productMainImage = productMainImageRepository.findByProduct_productno(no);
            // System.out.println(image2);
            if (productMainImage.getProductmainimage().length > 0) {
                HttpHeaders headers = new HttpHeaders();
                if (productMainImage.getProductmainimagetype().equals("image/jpeg")) {
                    headers.setContentType(MediaType.IMAGE_JPEG);
                } else if (productMainImage.getProductmainimagesize().equals("image/png")) {
                    headers.setContentType(MediaType.IMAGE_PNG);
                }
                ResponseEntity<byte[]> response = new ResponseEntity<>(productMainImage.getProductmainimage(), headers,
                        HttpStatus.OK);
                return response;
            }
            return null;
        }
        // 오라클에 이미지를 읽을 수 없을 경우
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // 위시리스트 삭제
    // 127.0.0.1:8080/HOST/wish/wish_delete.json?no=
    @DeleteMapping(value = "/wish_delete.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteWish(@RequestParam("no") Long no) {
        // System.out.println(no);
        Map<String, Object> map = new HashMap<>();
        try {
            memberWishService.MemberWishDelete(no);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
}
