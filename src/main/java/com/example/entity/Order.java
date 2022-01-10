package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "ORDER")
@SequenceGenerator(name = "SEQ_ORDER", sequenceName = "SEQ_ORDER_NO", initialValue = 1, allocationSize = 1)
public class Order {
    
    @Id
    @Column(name = "ORDER_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ORDER_NO")
    private Long orderno;

    @Column(name = "ORDER_PRODUCTOPTION_NO")
    private String order_productoption_no;

    @Column(name = "ORDER_PRICE")
    private long orderprice;

    @Column(name = "ORDER_DISCOUNTED_AMOUNT")
    private long order_discounted_amount;

    @Column(name = "ORDER_PRODUCTDELIVERYFEE")
    private Long order_productdeliveryfee;

    @Column(name = "ORDER_PRODUCTDELIVERYFEE_CHECK")
    private String order_productdeliveryfee_check;

    @Column(name = "ORDER_USERADDRESS")
    private String order_useraddress;

    @Column(name = "ORDER_RECIEVER_NAME")
    private String order_reciever;

    @Column(name = "ORDER_RECIEVER_PHONE")
    private String order_reciever_phone;

    @Column(name = "ORDER_IDX_NO")
    private int order_idx_no;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private Product USER_ID;

}
