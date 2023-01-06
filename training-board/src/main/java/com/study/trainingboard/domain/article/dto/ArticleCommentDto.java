package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.domain.article.model.entity.ArticleComment;
import com.study.trainingboard.domain.article.model.entity.UserAccount;

import java.time.LocalDateTime;

public record ArticleCommentDto(
        Long id,
        Long articleId,
        UserAccountDto userAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

    public static ArticleCommentDto of(
            Long articleId,
            UserAccountDto userAccountDto,
            String content
    ) {
        return new ArticleCommentDto(
                null,
                articleId,
                userAccountDto,
                content,
                null,
                null,
                null,
                null
        );
    }

    public static ArticleCommentDto of(
            Long id,
            Long articleId,
            UserAccountDto userAccountDto,
            String content,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new ArticleCommentDto(
                id,
                articleId,
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
                articleComment.getArticle().getId(),
                UserAccountDto.from(articleComment.getUserAccount()),
                articleComment.getContent(),
                articleComment.getCreatedAt(),
                articleComment.getCreatedBy(),
                articleComment.getUpdatedAt(),
                articleComment.getUpdatedBy()
        );
    }

    public ArticleComment toEntity(
            Article article,
            UserAccount userAccount
    ) {
        return ArticleComment.of(
                content,
                article,
                userAccount
        );
    }
}


