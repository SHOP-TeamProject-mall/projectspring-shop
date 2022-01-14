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

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "REVIEW")
@SequenceGenerator(name = "SEQ_REVIEW", sequenceName = "SEQ_REVIEW_NO", initialValue = 1, allocationSize = 1)
public class Review {
    
    @Id
    @Column(name = "REVIEW_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REVIEW_NO")
    private Long reviewno;

    @Column(name = "REVIEW_CONTENT")
    private String reviewcontent;

    @ManyToOne
    @JoinColumn(name = "PRODUCTORDERNUMBER")
    private ProductOrder productOrder;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
