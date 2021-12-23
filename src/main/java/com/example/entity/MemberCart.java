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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "MEMBERCART")
@SequenceGenerator(name = "SEQ_MEMBERCART", sequenceName = "SEQ_MEMBERCART_NO", initialValue = 1, allocationSize = 1)

public class MemberCart {

    @Id
    @Column(name = "MEMBERCART_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CART")
    private Long membercartno = 0L;

    @Column(name = "CART_COUNT")
    private Long cartcount = 0L;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_NO")
    private Product product = null;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member = null;

    @Column(name = "CREATE_TIME", updatable = false)
    @CreationTimestamp
    private Date createtime;

}
