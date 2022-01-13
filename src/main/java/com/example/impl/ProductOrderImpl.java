package com.example.impl;

import com.example.entity.ProductOrder;
import com.example.repository.ProductOrderRepository;
import com.example.service.ProductOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOrderImpl implements ProductOrderService{

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Override
    public ProductOrder insertProductOrder(ProductOrder productOrder) {
        try{
            return productOrderRepository.save(productOrder);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    
    
}
