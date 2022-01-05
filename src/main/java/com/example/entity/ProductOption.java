package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@SequenceGenerator(name = "SEQ_PRODUCT_OPTION", sequenceName = "SEQ_PRODUCTOPTION_NO", initialValue = 1, allocationSize = 1)

@Table(name = "PRODUCTOPTION")
public class ProductOption {
    
    @Id
    @Column(name = "PRODUCTOPTION_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTOPTION_NO")
    private Long productoptionno;

    @Column(name = "PRODUCTOPTION_NAME")
    private String productoptionname;

    @Column(name = "PRODUCTOPTION_SIZE")
    private String productoptionsize;
    
    @Column(name = "PRODUCTOPTION_COLOR")
    private String productoptioncolor;

    @Column(name = "PRODUCTOPTION_ADDITIONAL_AMOUNT")
    private Long productoptionadditionalamount;

    @Column(name = "PRODUCT_DATE")
    @CreationTimestamp
    private Date productdate = null;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;

    // @OneToMany(mappedBy = "ProductOption", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    // private ProductOptionImage ProductOptionImagelist = new ProductOptionImage();
}
