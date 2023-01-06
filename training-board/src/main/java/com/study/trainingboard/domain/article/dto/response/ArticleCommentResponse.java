package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;

import java.time.LocalDateTime;

public record ArticleCommentResponse(
        Long id,
        String content,
        String nickname,
        String userId,
        LocalDateTime createdAt
) {

    public static ArticleCommentResponse of(
            Long id,
            String content,
            String nickname,
            String userId,
            LocalDateTime createdAt
    ) {
        return new ArticleCommentResponse(
                id,
                content,
                nickname,
                userId,
                createdAt
        );
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        return new ArticleCommentResponse(
                dto.getId(),
                dto.getContent(),
                dto.getUserAccountDto().getNickname(),
                dto.getUserAccountDto().getEmail(),
                dto.getCreatedAt()
        );
    }
}

