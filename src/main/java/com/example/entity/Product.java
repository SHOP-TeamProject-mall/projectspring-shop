package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@SequenceGenerator(name = "SEQ_PRODUCT", sequenceName = "SEQ_PRODUCT_NO", initialValue = 1, allocationSize = 1)

@Table(name = "PRODUCT")
public class Product {
    
    @Id
    @Column(name = "PRODUCT_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT_NO")
    private Long productno;

    @Column(name = "PRODUCT_TITLE")
    private String producttitle;

    @Column(name = "PRODUCT_CATEGORY")
    private String productcategory;

    @Column(name = "PRODUCT_BRAND")
    private String productbrand;

    @Column(name = "PRODUCT_PRICE")
    private Long productprice;

    @Column(name = "PRODUCT_FABRIC")
    private String productfabric;

    @Column(name = "PRODUCT_QUANTITY")
    private Long productquantity;

    @Column(name = "DELIVERYFEE")
    private Long productdeliveryfee;

    @Column(name = "PRODUCT_SALE")
    private Double productsale;

    @Column(name = "PRODUCT_FINALPRICE")
    private Long productfinalprice;

    @Column(name = "PRODUCT_SUBIMAGE_IDX")
    private int productsubimageidx;

    @Column(name = "PRODUCT_DATE")
    @CreationTimestamp
    private Date productdate = null;
}
