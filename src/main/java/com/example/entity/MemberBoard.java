package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@NoArgsConstructor
@ToString

@Table(name = "BOARDS") // 테이블 명
@SequenceGenerator(name = "SEQ_BOARDS", sequenceName = "SEQ_BOARDS_NO", initialValue = 1, allocationSize = 1)

public class MemberBoard {

    @Column(name = "BOARDS") // 테이블 내 컬럼 명
    @Id // 기본키 (중복 안됨)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARDS")
    private long no;

    @Column(name = "TITLE")
    private String title = null; // 글제목

    @Lob
    @Column(name = "CONTENT")
    private String content = null; // 내용

    @Column(name = "WRITER")
    private String writer = null; // 작성자

    @Column(name = "HIT")
    private int hit = 1; // 조회수

    @CreationTimestamp // 날짜는 자동으로 추가
    @Column(updatable = false, name = "REGDATE")
    private Date regdate = null; // 날짜

}
