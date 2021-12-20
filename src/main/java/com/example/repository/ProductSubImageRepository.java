package com.example.repository;

import com.example.entity.ProductSubImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSubImageRepository extends JpaRepository<ProductSubImage, Long>{
    
    ProductSubImage findByProduct_productno(Long no);

}
