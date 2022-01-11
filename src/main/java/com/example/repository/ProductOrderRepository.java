package com.example.repository;

import com.example.entity.ProductOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{
    ProductOrder findByOrderno(long no);
}
