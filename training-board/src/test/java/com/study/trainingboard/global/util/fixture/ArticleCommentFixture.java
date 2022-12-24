package com.study.trainingboard.global.util.fixture;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import com.study.trainingboard.domain.article.model.entity.ArticleComment;

import java.time.LocalDateTime;

public class ArticleCommentFixture {

    public static ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                content,
                ArticleFixture.createArticle(),
                UserAccountFixture.createUserAccount()
        );
    }

    public static ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L,
                1L,
                UserAccountFixture.createUserAccountDto(),
                content,
                LocalDateTime.now(),
                "pepe",
                LocalDateTime.now(),
                "pepe"
        );
    }
}
