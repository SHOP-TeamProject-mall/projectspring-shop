package com.example.repository;

import java.util.List;

import com.example.entity.Product;
import com.example.entity.ProductOrder;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{
    ProductOrder findByOrderno(long no);

    List<ProductOrder> findByIdxIgnoreCaseContainingAndUseridIgnoreCaseContaining(long idx, String userid);

    List<ProductOrder> findByUseridOrderByOrdernoDesc(String userid, Pageable pageable);

    long countByUseridContaining(String userid);

    ProductOrder findByOrdernumber(String ordernumber);
}
