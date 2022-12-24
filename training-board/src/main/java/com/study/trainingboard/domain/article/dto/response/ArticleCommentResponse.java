package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCommentResponse {
    private final Long id;
    private final String content;
    private final String nickname;
    private final LocalDateTime createdAt;

    private ArticleCommentResponse(
            Long id,
            String content,
            String nickname,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public static ArticleCommentResponse of(
            Long id,
            String content,
            String nickname,
            LocalDateTime createdAt
    ) {
        return new ArticleCommentResponse(
                id,
                content,
                nickname,
                createdAt
        );
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        return new ArticleCommentResponse(
                dto.getId(),
                dto.getContent(),
                dto.getUserAccountDto().getNickname(),
                dto.getCreatedAt()
        );
    }
}
