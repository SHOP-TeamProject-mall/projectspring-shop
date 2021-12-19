package com.example.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.entity.Product;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(value = "/product")
public class ProductController {
    
    @Autowired
    ProductService pService;

    //127.0.0.1:8080/HOST/product/insertproduct.json
    @PostMapping(value="/insertproduct.json", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object insertProduct(@RequestBody Product product) {
        Map<String, Object> map = new HashMap<>();
        try{
            long no = pService.inserProduct(product);

            map.put("status", 200);
            map.put("no", no);
        }
        catch(Exception e){
            e.printStackTrace();
            map.put("status", e.hashCode());
        }
        return map;
    }
    
}
