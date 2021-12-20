package com.example.impl;

import java.util.List;

import com.example.entity.ProductOption;
import com.example.repository.ProductOptionRepository;
import com.example.service.ProductOptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionImpl implements ProductOptionService{

    @Autowired
    ProductOptionRepository productOptionRepository;

    @Override
    public ProductOption inserProductOption(ProductOption productOption) {
        try{
            return productOptionRepository.save(productOption);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductOption> selectProductOptionList() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
