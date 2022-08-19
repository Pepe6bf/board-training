package com.spadeworker.boardpractice.controller;

import com.spadeworker.boardpractice.domain.SearchType;
import com.spadeworker.boardpractice.dto.ArticleDto;
import com.spadeworker.boardpractice.dto.response.ArticleResponse;
import com.spadeworker.boardpractice.dto.response.ArticleWithCommentsResponse;
import com.spadeworker.boardpractice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 게시글 리스트 출력
     * 검색/페이지 입력
     */
    @GetMapping()
    public List<ArticleResponse> getArticles(
            @RequestParam(required = false, value = "search_type") SearchType searchType,
            @RequestParam(required = false, value = "search_value") String searchValue,
            @PageableDefault
                    (size = 10, sort = "createdAt", direction = Sort.Direction.DESC
                    ) Pageable pageable
    ) {
         return articleService.searchArticles(searchType, searchValue, pageable)
                .map(ArticleResponse::from).getContent();
    }

    /**
     * 해시태그로 게시글 검색
     */
    @GetMapping("/search-hashtag")
    public List<ArticleDto> searchHashtag(
            @RequestParam(required = false, value = "search_value") String searchValue,
            @PageableDefault
                    (size = 10, sort = "createdAt", direction = Sort.Direction.DESC
                    ) Pageable pageable
    ) {
        return articleService.searchArticlesViaHashtag(searchValue, pageable).getContent();
    }

    /**
     * 게시글 단건 상세 조회
     * 게시글 관련 댓글도 모두 조회됨
     */
    @GetMapping("/{articleId}")
    public ArticleWithCommentsResponse getArticle(@PathVariable Long articleId) {
        return ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
    }
}
