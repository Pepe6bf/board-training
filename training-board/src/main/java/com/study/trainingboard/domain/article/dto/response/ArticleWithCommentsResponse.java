package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleWithCommentsDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ArticleWithCommentsResponse  {
    private final Long id;
    private final String title;
    private final String content;
    private final String hashtag;
    private final LocalDateTime createdAt;
    private final String nickname;
    private final Set<ArticleCommentResponse> articleCommentsResponse;

    private ArticleWithCommentsResponse(
            Long id,
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String nickname,
            Set<ArticleCommentResponse> articleCommentsResponse
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
        this.nickname = nickname;
        this.articleCommentsResponse = articleCommentsResponse;
    }

    public static ArticleWithCommentsResponse of(
            Long id,
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String nickname,
            Set<ArticleCommentResponse> articleCommentsResponse
    ) {
        return new ArticleWithCommentsResponse(
                id,
                title,
                content,
                hashtag,
                createdAt,
                nickname,
                articleCommentsResponse
        );
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        return new ArticleWithCommentsResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getNickname(),
                dto.getArticleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
