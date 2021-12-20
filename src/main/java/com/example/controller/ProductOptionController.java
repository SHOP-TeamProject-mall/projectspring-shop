package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Product;
import com.example.entity.ProductOption;
import com.example.repository.ProductOptionRepository;
import com.example.repository.ProductRepository;
import com.example.service.ProductOptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/productoption")
public class ProductOptionController {

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductOptionService productOptionService;
    
    // 127.0.0.1:8080/HOST/productoption/insert_productoption.json
    @PostMapping(value="/insert_productoption.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Object> InsertProductOption (
        @RequestBody ProductOption productOption,
        @RequestParam(name = "productno") long productno ) throws IOException {
            System.out.println("fdsfdsf");
        Map<String,Object> map = new HashMap<>();
        try{
            Product product = productRepository.findByProductno(productno);
            productOption.setProduct(product);

            ProductOption productOption2 = productOptionService.inserProductOption(productOption);
            map.put("status", 200);
            map.put("option", productOption2);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
}
