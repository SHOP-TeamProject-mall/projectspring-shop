package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "MEMBER")

public class Member {
    
    @Id
    @Column(name = "MEMBER_ID")
    private String memberid;

    @Column(name = "MEMBER_PW")
    private String memberpw;

    @Column(name = "MEMBER_NAME")
    private String membername = null;

    @Column(name = "MEMBER_EMAIL")
    private String memberemail = null;

    @Column(name = "MEMBER_ADDRESS")
    private String memberaddress = null;

    @Column(name = "MEMBER_PHONE")
    private String memberphone = null;

    @Column(name = "MEMBER_ROLE")
    private String memberrole = null;

    @Column(name = "CREATETIME", updatable = false)
    @CreationTimestamp
    private Date createtime;

}
