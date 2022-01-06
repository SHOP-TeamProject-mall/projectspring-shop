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
import javax.persistence.OneToOne;
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
@Table(name = "PRODUCTSUBIMAGE")
@SequenceGenerator(name = "SEQ_PRODUCT_SUBIMAGE", sequenceName = "SEQ_PRODUCTSUBIMAGE_NO", initialValue = 1, allocationSize = 1)
public class ProductSubImage {
    
    @Id // 기본키
    @Column(name = "PRODUCTSUBIMAGE_NO") // 컬럼명 : Number
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTSUBIMAGE_NO") // 시퀀스의 값으로 추가됨
    private Long productsubimageno;

    @Lob
    @Column(name = "PRODUCTSUBIMAGE_IMAGE") // 물품명 VARCHAR2(100)
    private byte[] productsubimage = null;

    @Lob // String 이면 clob, byte[]이면 blob
    @Column(name = "PRODUCTSUBIMAGE_NAME") // CLOB 길이가 아주 긴 문자열
    private String productsubimagename = null;

    @Column(name = "PRODUCTSUBIMAGE_SIZE", nullable = false, columnDefinition = "long default 0")
    private Long productsubimagesize;

    @Column(name = "PRODUCTSUBIMAGE_TYPE")
    private String productsubimagetype = null; // 파일 종류

    @Column(name = "PRODUCTSUBIMAGE_IDX")
    private int productsubimageidx;

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @OneToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product;
}