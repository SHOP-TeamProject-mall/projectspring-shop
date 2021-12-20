package com.example.repository;

import com.example.entity.Product;
import com.example.entity.ProductOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>{
    
    Product findByProduct_productno(Long no);
}
