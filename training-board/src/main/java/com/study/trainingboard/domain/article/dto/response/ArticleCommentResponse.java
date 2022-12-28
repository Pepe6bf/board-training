package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCommentResponse {
    private final Long id;
    private final String content;
    private final String nickname;

    private final String userId;
    private final LocalDateTime createdAt;

    private ArticleCommentResponse(
            Long id,
            String content,
            String nickname,
            String userId,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
        this.userId = userId;
        this.createdAt = createdAt;
    }

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
