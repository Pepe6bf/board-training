package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String hashtag;
    private final String nickname;
    private final LocalDateTime createdAt;

    private ArticleResponse(
            Long id,
            String title,
            String content,
            String hashtag,
            String nickname,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public static ArticleResponse of(
            Long id,
            String title,
            String content,
            String hashtag,
            String nickname,
            LocalDateTime createdAt
    ) {
        return new ArticleResponse(
                id,
                title,
                content,
                hashtag,
                nickname,
                createdAt
        );
    }

    public static ArticleResponse from(ArticleDto dto) {
        String nickname = dto.getUserAccountDto().getNickname();

        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getUserAccountDto().getNickname(),
                dto.getCreatedAt()
        );
    }
}
