package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Member;
import com.example.entity.MemberCart;
import com.example.entity.Product;
import com.example.entity.ProductMainImage;
import com.example.jwt.JwtUtil;
import com.example.repository.CartRepository;
import com.example.repository.MemberRepository;
import com.example.repository.ProductMainImageRepository;
import com.example.repository.ProductRepository;
import com.example.service.MemberCartService;
import com.example.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/cart")
@RestController
public class CartController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberCartService memberCartService;

    @Autowired
    ProductMainImageRepository productMainImageRepository;

    // 장바구니 카트 추가하기
    // 127.0.0.1:8080/HOST/cart/memberinsertcart.json?no=
    @PostMapping(value = "/memberinsertcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberInsertCart(@RequestParam("no") long no, @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {

            String memberid = jwtUtil.extractUsername(token); // 토큰 회원 아이디 추출
            Member member = memberRepository.getById(memberid); // 회원 아이디
            Product product = productRepository.getById(no); // 상품 코드

            MemberCart memberCart = cartRepository.findByMember_memberid_AndProduct_productno(memberid,
                    product.getProductno());

            if (memberCart == null) {
                memberCart = new MemberCart();
                memberCart.setMember(member);
                memberCart.setProduct(product);
                memberCart.setCartcount(1L);
                cartRepository.save(memberCart);
            } else {

                if (memberCart.getCartcount() == 0) {
                    memberCart.setCartcount(1L);
                } else if (memberCart.getCartcount() == 1) {
                    memberCart.setCartcount(0L);
                }
                cartRepository.save(memberCart);
            }
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 장바구니 카트 리스트 조회하기
    // 127.0.0.1:8080/HOST/cart/memberselectcart.json
    @GetMapping(value = "/memberselectcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberSelectCart(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        try {
            String userid = jwtUtil.extractUsername(token);
            map.put("membercart", memberCartService.MemberCartSelect(userid));
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 장바구니 카트 리스트 중 삭제하기
    // 127.0.0.1:8080/HOST/cart/memberdeletecart.json?membercartno=
    @DeleteMapping(value = "/memberdeletecart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> MemberDeleteCart(@RequestParam("membercartno") Long membercartno) {
        Map<String, Object> map = new HashMap<>();
        try {
            memberCartService.MemberCartDelete(membercartno);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 장바구니 이미지 조회
    // 127.0.0.1:8080/HOST/cart/cartselect_image?no=번호
    @RequestMapping(value = "/cartselect_image", method = RequestMethod.GET)
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

}
