package com.spadeworker.boardpractice.domain;


import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String title;   // 제목
    private String content; // 본문
    private String hashtag; // 해시태그

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
