package com.example.controller;

import com.example.entity.ProductOrder;
import com.example.repository.ProductOrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/order")
public class ProductOrderController {

    @Autowired
    ProductOrderRepository productOrderRepository;
    
    // 127.0.0.1:8080/HOST/order/insertorder.json
    @PostMapping(value="/insertorder.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> postMethodName() {
        Map<String, Object> map = new HashMap<>();
        try{
            // 주문번호 자동생성
            long nowTime1 = System.currentTimeMillis()/10000000;
            long randomnumber = (long)(Math.random() * 999999);
            String ordernowtime = String.valueOf(nowTime1);
            String orderrandomnumber = String.valueOf(randomnumber);


            ProductOrder productOrder2 = new ProductOrder();
            productOrder2.setOrdernumber(ordernowtime+orderrandomnumber);
            productOrderRepository.save(productOrder2);

            map.put("status", 200);
        } 
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
}
