package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.ArticleComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCommentDto {
    private Long id;

    private UserAccountDto userAccountDto;

    private final String content;

    private final LocalDateTime createdAt;

    private final String createdBy;

    private final LocalDateTime updatedAt;

    private final String updatedBy;

    private ArticleCommentDto(
            Long id,
            UserAccountDto userAccountDto,
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.id = id;
        this.userAccountDto = userAccountDto;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static ArticleCommentDto of(
            Long id,
            UserAccountDto userAccountDto,
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new ArticleCommentDto(
                id,
                userAccountDto,
                content,
                createdAt,
                createdBy,
                updatedAt,
                updatedBy
        );
    }

    public static ArticleCommentDto from(ArticleComment articleComment) {
        return new ArticleCommentDto(
                articleComment.getId(),
                UserAccountDto.from(articleComment.getUserAccount()),
                articleComment.getContent(),
                articleComment.getCreatedAt(),
                articleComment.getCreatedBy(),
                articleComment.getUpdatedAt(),
                articleComment.getUpdatedBy()
        );
    }
}
