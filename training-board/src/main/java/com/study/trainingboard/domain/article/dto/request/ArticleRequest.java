package com.study.trainingboard.domain.article.dto.request;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.dto.UserAccountDto;
import lombok.Getter;

@Getter
public class ArticleRequest {

    private final String title;

    private final String content;

    private final String hashtag;

    private ArticleRequest(
            String title,
            String content,
            String hashtag
    ) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static ArticleRequest of(
            String title,
            String content,
            String hashtag
    ) {
        return new ArticleRequest(title, content, hashtag);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtag
        );
    }
}
