package com.study.trainingboard.global.util.fixture;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.model.entity.Article;

import java.time.LocalDateTime;

public class ArticleFixture {

    public static Article createArticle() {
        return Article.of(
                "title",
                "content",
                "#java",
                UserAccountFixture.createUserAccount()
        );
    }

    public static ArticleDto createArticleDto() {
        return createArticleDto(
                "title",
                "content",
                "#java"
        );
    }

    public static ArticleDto createArticleDto(
            String title,
            String content,
            String hashtag
    ) {
        return ArticleDto.of(
                1L,
                UserAccountFixture.createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "tester",
                LocalDateTime.now(),
                "tester"
        );
    }
}
