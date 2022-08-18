package com.spadeworker.boardpractice.dto;

import com.spadeworker.boardpractice.domain.Article;

public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
) {
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }

    public static ArticleUpdateDto from(Article entity) {
        return new ArticleUpdateDto(
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag()
        );
    }

    public Article toEntity() {
        return Article.of(
                title,
                content,
                hashtag
        );
    }
}
