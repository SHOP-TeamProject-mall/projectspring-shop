package com.example.repository;

import com.example.entity.ProductMainImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMainImageRepository extends JpaRepository<ProductMainImage, Long>{
    
    ProductMainImage findByProduct_productno(Long no);
    
}
