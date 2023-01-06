package com.study.trainingboard.domain.article.dto.request;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import com.study.trainingboard.domain.article.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }

}

