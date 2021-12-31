package com.example.service;

import java.util.List;

import com.example.entity.Product;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    
    public long inserProduct(Product product);

    public List<Product> selectProductList();

    public Product selectOneProduct(long no);

    public int UpdateProduct(Product product);
}
