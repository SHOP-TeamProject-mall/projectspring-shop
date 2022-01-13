package com.example.service;

import com.example.entity.ProductOrder;

import org.springframework.stereotype.Service;

@Service
public interface ProductOrderService {
    
    public ProductOrder insertProductOrder(ProductOrder productOrder);

}
