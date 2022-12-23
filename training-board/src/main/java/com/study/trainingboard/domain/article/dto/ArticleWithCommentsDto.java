package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.Article;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ArticleWithCommentsDto {
    Long id;

    UserAccountDto userAccountDto;

    Set<ArticleCommentDto> articleCommentDtos;

    String title;

    String content;

    String hashtag;

    LocalDateTime createdAt;

    String createdBy;

    LocalDateTime updatedAt;

    String updatedBy;

    private ArticleWithCommentsDto(
            Long id,
            UserAccountDto userAccountDto,
            Set<ArticleCommentDto> articleCommentDtos,
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
        this.articleCommentDtos = articleCommentDtos;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static ArticleWithCommentsDto of(
            Long id,
            UserAccountDto userAccountDto,
            Set<ArticleCommentDto> articleCommentDtos,
            String title,
            String content,
            String hashtag,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new ArticleWithCommentsDto(
                id,
                userAccountDto,
                articleCommentDtos,
                title,
                content,
                hashtag,
                createdAt,
                createdBy,
                updatedAt,
                updatedBy
        );
    }

    public static ArticleWithCommentsDto from(Article article) {
        return new ArticleWithCommentsDto(
                article.getId(),
                UserAccountDto.from(article.getUserAccount()),
                article.getArticleComments()
                        .stream()
                        .map(ArticleCommentDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                article.getTitle(),
                article.getContent(),
                article.getHashtag(),
                article.getCreatedAt(),
                article.getCreatedBy(),
                article.getUpdatedAt(),
                article.getUpdatedBy()
        );
    }
}
