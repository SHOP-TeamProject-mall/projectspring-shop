package com.example.repository;


import java.util.List;

import com.example.entity.ProductSubImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSubImageRepository extends JpaRepository<ProductSubImage, Long>{
    
    ProductSubImage findByProduct_productno(Long no);

    ProductSubImage findByProductsubimageidx(int idx);

    ProductSubImage findByProduct_productnoAndProductsubimageidx(long no, int idx);
    
    List<ProductSubImage> countByProductsubimageidx(int idx);
}
