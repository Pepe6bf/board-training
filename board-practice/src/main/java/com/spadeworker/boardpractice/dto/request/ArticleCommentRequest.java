package com.spadeworker.boardpractice.dto.request;

import com.spadeworker.boardpractice.dto.ArticleCommentDto;
import com.spadeworker.boardpractice.dto.UserAccountDto;

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
