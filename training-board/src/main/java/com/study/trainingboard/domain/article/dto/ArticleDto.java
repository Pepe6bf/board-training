package com.study.trainingboard.domain.article.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDto {

    private final String title;

    private final String content;

    private final String hashtag;

    private final LocalDateTime createdAt;

    private final String createdBy;

    private final LocalDateTime updatedAt;

    private final String updatedBy;

    private ArticleDto(
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static ArticleDto of(
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new ArticleDto(
                title,
                content,
                hashtag,
                createdAt,
                createdBy,
                updatedAt,
                updatedBy
        );
    }
}
