package com.spadeworker.boardpractice.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id;
    private Article articleId;  // 게시글 (ID)
    private String content; // 본문

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
