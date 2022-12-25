package com.study.trainingboard.domain.article.controller.view;

import com.study.trainingboard.domain.article.dto.UserAccountDto;
import com.study.trainingboard.domain.article.dto.request.ArticleCommentRequest;
import com.study.trainingboard.domain.article.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
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
            ArticleCommentRequest request
    ) {
        // TODO: 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(request.toDto(
                UserAccountDto.of(
                        "pepe@email.com",
                        "pepePw1234!",
                        "pepe"
                )
        ));

        return "redirect:/articles/" + request.getArticleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(
            @PathVariable Long commentId,
            Long articleId
    ) {
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" + articleId;
    }
}
