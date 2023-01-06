package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleDto;

import java.time.LocalDateTime;

public record ArticleResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        String nickname,
        LocalDateTime createdAt
) {

    public static ArticleResponse of(
            Long id,
            String title,
            String content,
            String hashtag,
            String nickname,
            LocalDateTime createdAt
    ) {
        return new ArticleResponse(id, title, content, hashtag, nickname, createdAt);
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.userAccountDto().nickname();

        return new ArticleResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.userAccountDto().nickname(),
                dto.createdAt()
        );
    }
}
