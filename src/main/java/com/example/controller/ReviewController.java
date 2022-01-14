package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.Member;
import com.example.entity.ProductOrder;
import com.example.entity.Review;
import com.example.jwt.JwtUtil;
import com.example.repository.MemberRepository;
import com.example.repository.ProductOrderRepository;
import com.example.repository.ReviewRepository;
import com.example.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping(value = "/review")
public class ReviewController {

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository repository;
    
    // 127.0.0.1:8080/HOST/review/insert_review.json?ordernumber=
    @PostMapping(value="/insert_review.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> InsertReview(
        @RequestBody Review review,
        @RequestHeader("token") String token,
        @RequestParam(name = "ordernumber") String ordernumber ) {
        Map<String, Object> map = new HashMap<>();
        try{
            ProductOrder productOrder = productOrderRepository.findByOrdernumber(ordernumber);
            String memberid = jwtUtil.extractUsername(token);
            Member member = memberRepository.findByMemberid(memberid);

            review.setMember(member);
            review.setProductOrder(productOrder);

            Review review2 = reviewService.InsertReview(review);
            map.put("review", review2);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }

    // 127.0.0.1:8080/HOST/review/select_review.json
    @GetMapping(value="/select_review.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> SelectReview(@RequestParam(name = "page", defaultValue = "0") int page) {
        Map<String, Object> map = new HashMap<>();
        Sort sort = Sort.by("reviewno").descending();
        PageRequest pageable = PageRequest.of(page - 1, 9,sort);
        try{
            // System.out.println("99999999999999999999999");
            Page<Review> review = repository.findAll(pageable);
            
            long pages = review.getSize();
            System.out.println(review.toString());
            // System.out.println("11111111111111111111111111"+pages);
            map.put("review", review);
            map.put("pages", (pages - 1) / 9 + 1);
            map.put("status", 200);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
    
}
