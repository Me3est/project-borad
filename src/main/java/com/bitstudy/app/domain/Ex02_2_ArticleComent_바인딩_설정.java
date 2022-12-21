package com.bitstudy.app.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@ToString
@Entity
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
public class Ex02_2_ArticleComent_바인딩_설정 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional=false) private Article article;

    @Setter
    @Column(nullable = false, length=500)
    private String content; // 본문

    // 메타데이터
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length=100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false) private
    LocalDateTime modifiedAt; // 생성일시

    @LastModifiedBy
    @Column(nullable = false, length=100)
    private String modifiedBy; // 수정자

}
