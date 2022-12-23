package com.study.trainingboard.domain.article.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCommentDto {

    private final String content;

    private final LocalDateTime createdAt;

    private final String createdBy;

    private final LocalDateTime updatedAt;

    private final String updatedBy;

    private ArticleCommentDto(
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static ArticleCommentDto of(
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new ArticleCommentDto(
                content,
                createdAt,
                createdBy,
                updatedAt,
                updatedBy);
    }
}
