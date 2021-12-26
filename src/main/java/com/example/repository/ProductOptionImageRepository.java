package com.example.repository;

import com.example.entity.ProductOptionImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionImageRepository extends JpaRepository<ProductOptionImage, Long>{
    
    ProductOptionImage findByProductOption_productoptionno(long no);
}
