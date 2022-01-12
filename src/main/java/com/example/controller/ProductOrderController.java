package com.example.controller;

import com.example.entity.ProductOrder;
import com.example.repository.ProductOrderRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(value = "/order")
public class ProductOrderController {

    @Autowired
    ProductOrderRepository productOrderRepository;
    
    // 127.0.0.1:8080/HOST/order/insertorder.json
    @PostMapping(value="/insertorder.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertOrder(
        @RequestBody ProductOrder productOrder
        ) {
        Map<String, Object> map = new HashMap<>();
        try{
            // 주문번호 자동생성
            long nowTime1 = System.currentTimeMillis()/10000000;
            long randomnumber = (long)(Math.random() * 999999);
            String ordernowtime = String.valueOf(nowTime1);
            String orderrandomnumber = String.valueOf(randomnumber);

            ProductOrder productOrder2 = new ProductOrder();
            productOrder2.setOrdernumber(ordernowtime+orderrandomnumber);
            productOrder2.setOrder_productoptionno(productOrder.getOrder_productoptionno());
            productOrder2.setOrder_productoptioncnt(productOrder.getOrder_productoptioncnt());
            productOrder2.setOrder_productoptionsize(productOrder.getOrder_productoptionsize());
            productOrder2.setOrder_productoptioncolor(productOrder.getOrder_productoptioncolor());
            productOrder2.setOrder_deliveryfee(productOrder.getOrder_deliveryfee());
            productOrder2.setOrder_deliveryfee_check(productOrder.getOrder_deliveryfee_check());
            productOrder2.setReciever_name(productOrder.getReciever_name());
            productOrder2.setReciever_phone(productOrder.getReciever_phone());
            productOrder2.setReciever_zipcode(productOrder.getReciever_zipcode());
            productOrder2.setReciever_address(productOrder.getReciever_address());
            productOrder2.setOrder_amount_paid(productOrder.getOrder_amount_paid());
            productOrder2.setReciever_detailed_address(productOrder.getReciever_detailed_address());
            productOrder2.setIdx(productOrder.getIdx());
            productOrder2.setUserid(productOrder.getUserid());

            productOrderRepository.save(productOrder2);
            
            map.put("status", 200);
        } 
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 결제완료 후 주문번호 (주문완료 페이지)
    // 127.0.0.1:8080/HOST/order/select_ordernumber.json?idx=&userid=
    @GetMapping(value="/select_ordernumber.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> SelectOrderNumber(
        @RequestParam(name = "idx", defaultValue = "") long idx,
        @RequestParam(name = "userid", defaultValue = "") String userid ) {
        Map<String, Object> map = new HashMap<>();
        try{
            List<ProductOrder> productOrder = productOrderRepository.findByIdxIgnoreCaseContainingAndUseridIgnoreCaseContaining(idx,userid);
            System.out.println(productOrder.toString());

            map.put("list", productOrder);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    
}
