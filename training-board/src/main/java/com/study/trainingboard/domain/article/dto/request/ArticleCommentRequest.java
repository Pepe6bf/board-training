package com.study.trainingboard.domain.article.dto.request;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import com.study.trainingboard.domain.article.dto.UserAccountDto;
import com.study.trainingboard.domain.article.model.entity.ArticleComment;
import lombok.Getter;

@Getter
public class ArticleCommentRequest {
    private final Long articleId;
    private final String content;

    private ArticleCommentRequest(Long articleId, String content) {
        this.articleId = articleId;
        this.content = content;
    }

    public static ArticleCommentRequest of(
            Long articleId,
            String content
    ) {
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
