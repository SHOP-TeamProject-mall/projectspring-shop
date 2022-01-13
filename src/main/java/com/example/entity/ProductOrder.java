package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "PRODUCT_ORDER")
@SequenceGenerator(name = "SEQ_PRODUCTORDER", sequenceName = "SEQ_PRODUCTORDER_NO", initialValue = 1, allocationSize = 1)
public class ProductOrder {
    
    @Id
    @Column(name = "ORDER_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_NO")
    private Long orderno;

    @Column(name = "ORDER_NUMBER")
    private String ordernumber;

    @Column(name = "PRODUCTNAME")
    private String productname;

    @Column(name = "ORDER_PRODUCTOPTIONNO")
    private Long order_productoptionno;

    @Column(name = "ORDER_PRODUCTOPTION_CNT")
    private Long order_productoptioncnt;

    @Column(name = "ORDER_PRODUCTOPTION_SIZE")
    private String order_productoptionsize;

    @Column(name = "ORDER_PRODUCTOPTION_COLOR")
    private String order_productoptioncolor;

    @Column(name = "ORDER_AMOUNT_PAID")
    private Long order_amount_paid;

    @Column(name = "ORDER_DELIVERYFEE")
    private Long order_deliveryfee;

    @Column(name = "ORDER_DELIVERYFEE_CHECK")
    private String order_deliveryfee_check;

    @Column(name = "RECIEVER_NAME")
    private String reciever_name;

    @Column(name = "RECIEVER_PHONE")
    private String reciever_phone;

    @Column(name = "RECIEVER_ZIPCODE")
    private String reciever_zipcode;

    @Column(name = "RECIEVER_ADDRESS")
    private String reciever_address;

    @Column(name = "RECIEVER_DETAILED_ADDRESS")
    private String reciever_detailed_address;

    @Column(name = "DELIVERY_STATUS")
    private String delivery_status = "배송준비중";

    @Column(name = "USER_ID")
    private String userid;

    @Column(name = "IDX")
    private String idx;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    @Column(name = "REGDATE")
    private Date regdate;

    @ManyToOne
    @JoinColumn(name = "PRODUCTOPTION_NO")
    private ProductOption productOption;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    // 옵션별 정보(번호만 가지고오기) / 옵션별 총 가격 / 선택 수량, 사이즈, 컬러 / 배송비(묶음 구매시 i+1번째 배송비는 0원) / 배송비 결제 선택 (선결제,착불) / 배송지 주소 및 상세주소 우편번호 / 수취인이름 연락처
    // 
    
}
