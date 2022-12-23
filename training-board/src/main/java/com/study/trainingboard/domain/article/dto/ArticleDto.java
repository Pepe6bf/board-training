package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDto {
    private final Long id;

    private final UserAccountDto userAccountDto;

    private final String title;

    private final String content;

    private final String hashtag;

    private final LocalDateTime createdAt;

    private final String createdBy;

    private final LocalDateTime updatedAt;

    private final String updatedBy;

    private ArticleDto(
            Long id,
            UserAccountDto userAccountDto,
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.id = id;
        this.userAccountDto = userAccountDto;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static ArticleDto of(
            Long id,
            UserAccountDto userAccountDto,
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new ArticleDto(
                id,
                userAccountDto,
                title,
                content,
                hashtag,
                createdAt,
                createdBy,
                updatedAt,
                updatedBy
        );
    }

    public static ArticleDto from(Article article) {
        return new ArticleDto(
                article.getId(),
                UserAccountDto.from(article.getUserAccount()),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getUpdatedAt(),
                article.getUpdatedBy()
        );
    }

    public Article toEntity() {
        return Article.of(
                title,
                content,
                hashtag,
                userAccountDto.toEntity()
        );
    }
}
