package com.study.trainingboard.domain.article.dto.response;

import com.study.trainingboard.domain.article.dto.ArticleWithCommentsDto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ArticleWithCommentsResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String nickname,
        String userId,
        Set<ArticleCommentResponse> articleCommentsResponse
) {

    public static ArticleWithCommentsResponse of(
            Long id,
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String nickname,
            String userId,
            Set<ArticleCommentResponse> articleCommentsResponse
    ) {
        return new ArticleWithCommentsResponse(
                id,
                title,
                content,
                hashtag,
                createdAt,
                nickname,
                userId,
                articleCommentsResponse
        );
    }

    public static ArticleWithCommentsResponse from(ArticleWithCommentsDto dto) {
        return new ArticleWithCommentsResponse(
                dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userAccountDto().getNickname(),
                dto.userAccountDto().getEmail(),
                dto.articleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}


