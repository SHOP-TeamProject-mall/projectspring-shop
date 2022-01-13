package com.example.controller;

import com.example.entity.Member;
import com.example.entity.Product;
import com.example.entity.ProductOption;
import com.example.entity.ProductOrder;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRepository;
import com.example.repository.ProductOptionRepository;
import com.example.repository.ProductOrderRepository;
import com.example.service.ProductOrderService;
import com.example.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(value = "/order")
public class ProductOrderController {

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductOrderService productOrderService;
    
    // 127.0.0.1:8080/HOST/order/insertorder.json
    @PostMapping(value="/insertorder.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertOrder(
        @RequestBody ProductOrder productOrder,
        @RequestHeader("token") String token,
        @RequestParam(name = "productoptionno") long productoptionno
        ) {
        Map<String, Object> map = new HashMap<>();
        try{
            // 주문번호 자동생성
            long nowTime1 = System.currentTimeMillis()/10000000;
            long randomnumber = (long)(Math.random() * 999999);
            String ordernowtime = String.valueOf(nowTime1);
            String orderrandomnumber = String.valueOf(randomnumber);

            ProductOption productOption = productOptionRepository.findByProductoptionno(productoptionno);
            // Product product = productOption.getProduct();
            // System.out.println(product+"9999999999999999999999999999999999999");
            String memberid = jwtUtil.extractUsername(token);
            Member member = memberRepository.findByMemberid(memberid);
            
            // ProductOrder productOrder2 = new ProductOrder();
            productOrder.setProductOption(productOption);
            productOrder.setMember(member);
            productOrder.setOrdernumber(ordernowtime+orderrandomnumber);

            ProductOrder productOrder2 = productOrderService.insertProductOrder(productOrder);
            // System.out.println(productOrder2.toString());
            // productOrder2.setOrder_productoptionno(productOrder.getOrder_productoptionno());
            // productOrder2.setOrder_productoptioncnt(productOrder.getOrder_productoptioncnt());
            // productOrder2.setOrder_productoptionsize(productOrder.getOrder_productoptionsize());
            // productOrder2.setOrder_productoptioncolor(productOrder.getOrder_productoptioncolor());
            // productOrder2.setOrder_deliveryfee(productOrder.getOrder_deliveryfee());
            // productOrder2.setOrder_deliveryfee_check(productOrder.getOrder_deliveryfee_check());
            // productOrder2.setReciever_name(productOrder.getReciever_name());
            // productOrder2.setReciever_phone(productOrder.getReciever_phone());
            // productOrder2.setReciever_zipcode(productOrder.getReciever_zipcode());
            // productOrder2.setReciever_address(productOrder.getReciever_address());
            // productOrder2.setOrder_amount_paid(productOrder.getOrder_amount_paid());
            // productOrder2.setReciever_detailed_address(productOrder.getReciever_detailed_address());
            // productOrder2.setIdx(productOrder.getIdx());
            // productOrder2.setUserid(productOrder.getUserid());
            // productOrder2.setProductname(productOrder.getProductname());

            // productOrderRepository.save(productOrder2);
            map.put("order", productOrder2);
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

    // 주문완료 후 내 주문내역 조회(아이디값이 일치한 것만)
    // 127.0.0.1:8080/HOST/order/select_myorderlist.json?page=
    @GetMapping(value="/select_myorderlist.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectMyOrderList(
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestHeader("token") String token ) {
            PageRequest pageable = PageRequest.of(page - 1, 10);
            String memberid = jwtUtil.extractUsername(token);
            
            List<ProductOrder> list = productOrderRepository.findByUseridOrderByOrdernoDesc(memberid, pageable);
            
            Map<String, Object> map = new HashMap<>();
        try{
            System.out.println("aaaaaaaa");
            long pages = productOrderRepository.countByUseridContaining(memberid);
            
            map.put("ppage", (pages - 1) / 10 + 1);
            map.put("list", list);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/order/select_myorderlist_one.json?ordernumber=
    @GetMapping(value = "/select_myorderlist_one.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectOneMyOrderList(
        @RequestParam(name = "ordernumber") String ordernumber
    ){
        Map<String, Object> map = new HashMap<>();
        try{
            ProductOrder productOrder = productOrderRepository.findByOrdernumber(ordernumber);

            map.put("orderlist", productOrder);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    
    
}
