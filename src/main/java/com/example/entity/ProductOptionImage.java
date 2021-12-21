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
@Table(name = "PRODUCTOPTIONIMAGE")
@SequenceGenerator(name = "SEQ_PRODUCT_OPTIONIMAGE", sequenceName = "SEQ_PRODUCTOPTIONIMAGE_NO", initialValue = 1, allocationSize = 1)
public class ProductOptionImage {
    
    @Id // 기본키
    @Column(name = "PRODUCTOPTIONIMAGE_NO") // 컬럼명 : Number
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCTOPTIONIMAGE_NO") // 시퀀스의 값으로 추가됨
    private Long productoptionimageno;

    @Lob
    @Column(name = "PRODUCTOPTIONIMAGE_IMAGE") // 물품명 VARCHAR2(100)
    private byte[] productoptionimage = null;

    @Lob // String 이면 clob, byte[]이면 blob
    @Column(name = "PRODUCTOPTIONIMAGE_NAME") // CLOB 길이가 아주 긴 문자열
    private String productoptionimagename = null;

    @Column(name = "PRODUCTOPTIONIMAGE_SIZE", nullable = false, columnDefinition = "long default 0")
    private Long productoptionimagesize;

    @Column(name = "PRODUCTOPTIONIMAGE_TYPE")
    private String productoptionimagetype = null; // 파일 종류

    // 날짜타입 포멧설정
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @ManyToOne
    @JoinColumn(name = "PRODUCTOPTION_NO")
    private ProductOption productOption;
}
