package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "PRODUCTMAINIMAGE")
@SequenceGenerator(name = "SEQ_PRODUCT_MAINIMAGE", sequenceName = "SEQ_PRODUCTMAINIMAGE_NO", initialValue = 1, allocationSize = 1)
public class ProductMainImage {
    
    @Id // 기본키
    @Column(name = "PRODUCTMAINIMAGE_NO") // 컬럼명 : Number
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTMAINIMAGE_NO") // 시퀀스의 값으로 추가됨
    private Long productmainimageno;

    @Lob
    @Column(name = "PRODUCTMAINIMAGE_IMAGE") // 물품명 VARCHAR2(100)
    private byte[] productmainimage = null;

    @Lob // String 이면 clob, byte[]이면 blob
    @Column(name = "PRODUCTMAINIMAGE_NAME") // CLOB 길이가 아주 긴 문자열
    private String productmainimagename = null;

    @Column(name = "PRODUCTMAINIMAGE_SIZE", nullable = false, columnDefinition = "long default 0")
    private Long productmainimagesize;

    @Column(name = "PRODUCTMAINIMAGE_TYPE")
    private String productmainimagetype = null; // 파일 종류

    @Column
    private String url;

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;
}
