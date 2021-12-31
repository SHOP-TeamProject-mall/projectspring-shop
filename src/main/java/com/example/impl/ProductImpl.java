package com.example.impl;

import java.util.List;

import com.example.entity.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImpl implements ProductService{

    @Autowired
    ProductRepository pRepository;

    @Override
    public long inserProduct(Product product) {
        try{
            Product ret = pRepository.save(product);
            return ret.getProductno();
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Product> selectProductList() {
        pRepository.findAll();
        return null;
    }

    @Override
    public Product selectOneProduct(long no) {
        return pRepository.findByProductno(no);
    }

    @Override
    public int UpdateProduct(Product product) {
        pRepository.save(product);
        return 0;
    }
    
}
