package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.Product;
import com.example.jwt.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

public class WishController {


    @Autowired
    JwtUtil jwtUtil;
    
    // 카트 추가하기
    // 127.0.0.1:8080/HOST/cart/insertcart.json?no=1
    @PutMapping(value = "/insertcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertProductPost(@RequestParam("no") long no, @RequestBody Product product,
            @RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        // try {
        //     // 토큰 회원 아이디 추출
        //     String id = jwtUtil.extractUsername(token);
        //     // 상품 코드 추출
        //     // Product product2 = pRepository.getById(product.getProductno());
        //     // Product product2 = pRepository.getById(no);
        //     // 회원 정보 꺼내기
        //     // Company company = companyRepository.getById(id);
        //     // 회원과 상품 코드가 있는지 없는지 체크
        //     Cart cart = cartRepository.findByCompany_companyidAndProduct_productno(id, product2.getProductno());
        //     // 없을 때
        //     if (cart == null) {
        //         cart = new Cart();
        //         cart.setProduct(product2);
        //         cart.setCompany(company);
        //         cart.setCartcount(1L);
        //         cartRepository.save(cart);
        //         // 있을 때
        //     } else {
        //         if (cart.getCartcount() == 0) {
        //             cart.setCartcount(1L);
        //         } else if (cart.getCartcount() == 1) {
        //             cart.setCartcount(0L);
        //         }
        //         cartRepository.save(cart);
        //     }
        //     map.put("status", 200);
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     map.put("status", e.hashCode());
        // }
        return map;
    }

    // 장바구니 리스트 조회
    // 127.0.0.1:8080/HOST/cart/selectcart.json
    @GetMapping(value = "/selectcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectcartListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
    //     try {
    //         String userid = jUtil.extractUsername(token);
    //         map.put("list", cService.selectCart(userid));
    //         map.put("status", 200);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         map.put("status", e.hashCode());
    //     }
         return map;
     }
}
