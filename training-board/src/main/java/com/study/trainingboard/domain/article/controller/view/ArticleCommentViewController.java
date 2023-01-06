package com.study.trainingboard.domain.article.controller.view;

import com.study.trainingboard.domain.article.dto.UserAccountDto;
import com.study.trainingboard.domain.article.dto.request.ArticleCommentRequest;
import com.study.trainingboard.domain.article.dto.security.BoardPrincipal;
import com.study.trainingboard.domain.article.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentViewController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            ArticleCommentRequest request
    ) {
        articleCommentService.saveArticleComment(request.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + request.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @AuthenticationPrincipal BoardPrincipal boardPrincipal,
            @PathVariable Long commentId,
            Long articleId
    ) {
        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getEmail());

        return "redirect:/articles/" + articleId;
    }
}
