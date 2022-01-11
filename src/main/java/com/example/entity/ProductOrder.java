package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.val;

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

    @Column(name = "ORDER_PRODUCTOPTIONNO")
    private Long order_productoptionno;

    @Column(name = "ORDER_PRODUCTOPTION_CNT")
    private Long order_productoptioncnt;

    @Column(name = "ORDER_PRODUCTOPTION_SIZE")
    private String order_productoptionsize;

    @Column(name = "ORDER_PRODUCTOPTION_COLOR")
    private String order_productoptioncolor;

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

    @Column(name = "USER_ID")
    private String user_id;
    // 옵션별 정보(번호만 가지고오기) / 옵션별 총 가격 / 선택 수량, 사이즈, 컬러 / 배송비(묶음 구매시 i+1번째 배송비는 0원) / 배송비 결제 선택 (선결제,착불) / 배송지 주소 및 상세주소 우편번호 / 수취인이름 연락처
    // 
    
}
