package com.study.trainingboard.domain.article.controller.view;

import com.study.trainingboard.domain.article.service.ArticleService;
import com.study.trainingboard.global.config.SecurityConfig;
import com.study.trainingboard.global.util.fixture.ArticleFixture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleViewController.class)
class ArticleViewControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    public ArticleViewControllerTest(@Autowired MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    void view_get_test() throws Exception {
        // Given
        given(articleService.searchArticles(
                any(Pageable.class),
                eq(null),
                eq(null))
        )
                .willReturn(Page.empty());

        // When & Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"));
        then(articleService).should().searchArticles(
                any(Pageable.class),
                eq(null),
                eq(null)
        );
    }

    @Test
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    void view_detail_page_test() throws Exception {
        // Given
        Long articleId = 1L;
        given(articleService.getArticle(articleId))
                .willReturn(ArticleFixture.createArticleWithCommentsDto());

        // When

        // Then
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"));
        then(articleService).should().getArticle(articleId);
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    void view_search_page_test() throws Exception {
        // Given

        // When

        // Then
        mockMvc.perform(get("/articles/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }

    @Disabled("구현 중")
    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    void view_hashtag_search_page_test() throws Exception {
        // Given

        // When

        // Then
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
    }
}