package com.example.repository;

import java.util.List;

import com.example.entity.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Product findByProductno(Long no);

    List<Product> findByProducttitleIgnoreCaseContainingOrProductbrandIgnoreCaseContainingOrderByProductnoDesc(String producttitle, String productbrand, Pageable pageable);

    long countByProducttitleContaining(String Producttitle);

    List<Product> findByProductcategoryIgnoreCaseContaining(String findByProductcategory);
}
