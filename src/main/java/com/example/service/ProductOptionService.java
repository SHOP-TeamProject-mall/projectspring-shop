package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import com.example.entity.Product;
import com.example.entity.ProductOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ProductOptionService {
    
    public ProductOption inserProductOption(ProductOption productOption);

    public List<ProductOption> selectProductOptionList();

    public List<ProductOption> selectProductOption_productno(Long productno);

    public int UpdateProductOption(ProductOption productOption);

}



