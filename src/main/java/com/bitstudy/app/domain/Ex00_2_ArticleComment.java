package com.bitstudy.app.domain;

import java.time.LocalDateTime;

public class Ex00_2_ArticleComment {

    private Long id;

    private Article article;

    private String content; // 본문

    // 메타데이터
    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 생성일시
    private String modifiedBy; // 수정자

}
