package com.study.trainingboard.domain.article.dto;

import lombok.Getter;

@Getter
public class ArticleUpdateDto {

    private final String title;

    private final String content;

    private final String hashtag;

    private ArticleUpdateDto(
            String title,
            String content,
            String hashtag
    ) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static ArticleUpdateDto of(
            String title,
            String content,
            String hashtag
    ) {
        return new ArticleUpdateDto(
                title,
                content,
                hashtag
        );
    }
}
