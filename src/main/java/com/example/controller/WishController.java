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
        return map;
    }

    // 장바구니 리스트 조회
    // 127.0.0.1:8080/HOST/cart/selectcart.json
    @GetMapping(value = "/selectcart.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object selectcartListGET(@RequestHeader("token") String token) {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
