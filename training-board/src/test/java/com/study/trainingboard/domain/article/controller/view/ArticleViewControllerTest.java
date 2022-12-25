package com.study.trainingboard.domain.article.controller.view;

import com.study.trainingboard.domain.article.model.constant.SearchType;
import com.study.trainingboard.domain.article.service.ArticleService;
import com.study.trainingboard.domain.article.service.PaginationService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    @MockBean
    private PaginationService paginationService;

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
        ).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(
                anyInt(),
                anyInt()
        )).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mockMvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        then(articleService).should().searchArticles(
                any(Pageable.class),
                eq(null),
                eq(null)
        );
        then(paginationService).should().getPaginationBarNumbers(
                anyInt(),
                anyInt()
        );
    }

    @Test
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 검색어와 함께 호출")
    void 게시글_검색_테스트() throws Exception {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        given(articleService.searchArticles(
                any(Pageable.class),
                eq(searchType),
                eq(searchValue)
        )).willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(
                anyInt(),
                anyInt()
        )).willReturn(List.of(0, 1, 2, 3, 4));

        // When & Then
        mockMvc.perform(
                        get("/articles")
                                .queryParam("searchType", searchType.toString())
                                .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));
        then(articleService).should().searchArticles(
                any(Pageable.class),
                eq(searchType),
                eq(searchValue)
        );
        then(paginationService).should().getPaginationBarNumbers(
                anyInt(),
                anyInt()
        );
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 페이징, 정렬 기능")
    @Test
    void 게시글_페이징_테스트() throws Exception {
        // Given
        String sortName = "title";
        String direction = "desc";
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Order.desc(sortName))
        );
        List<Integer> barNumbers = List.of(1, 2, 3, 4, 5);
        given(articleService.searchArticles(pageable, null, null))
                .willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(
                pageable.getPageNumber(),
                Page.empty().getTotalPages()
        )).willReturn(barNumbers);


        // When & Then
        mockMvc.perform(
                        get("/articles")
                                .queryParam("page", String.valueOf(pageNumber))
                                .queryParam("size", String.valueOf(pageSize))
                                .queryParam("sort", sortName + "," + direction)

                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attribute("paginationBarNumbers", barNumbers));
        then(articleService).should().searchArticles(pageable, null, null);
        then(paginationService).should().getPaginationBarNumbers(
                pageable.getPageNumber(),
                Page.empty().getTotalPages()
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

    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    void view_hashtag_search_page_test() throws Exception {
        // Given
        List<String> hashtags = List.of("#java", "#spring", "#boot");
        given(articleService.searchArticlesViaHashtag(any(Pageable.class), eq(null)))
                .willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(
                anyInt(),
                anyInt()
        )).willReturn(List.of(1, 2, 3, 4, 5));
        given(articleService.getHashtags())
                .willReturn(hashtags);

        // When & Then
        mockMvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attributeExists("hashtags"))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
        then(articleService).should().searchArticlesViaHashtag(any(Pageable.class), eq(null));
        then(paginationService).should()
                .getPaginationBarNumbers(
                        anyInt(),
                        anyInt()
                );
        then(articleService).should().getHashtags();
    }

    @Test
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출, 해시태그 입력")
    void view_hashtag_search_page_with_hashtag_test() throws Exception {
        // Given
        List<String> hashtags = List.of("#java", "#spring", "#boot");
        String hashtag = "#java";
        given(articleService.searchArticlesViaHashtag(any(Pageable.class), eq(hashtag)))
                .willReturn(Page.empty());
        given(paginationService.getPaginationBarNumbers(
                anyInt(),
                anyInt()
        )).willReturn(List.of(1, 2, 3, 4, 5));
        given(articleService.getHashtags())
                .willReturn(hashtags);

        // When & Then
        mockMvc.perform(get("/articles/search-hashtag")
                        .queryParam("searchValue", hashtag)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("articles/search-hashtag"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attribute("articles", Page.empty()))
                .andExpect(model().attributeExists("hashtags"))
                .andExpect(model().attributeExists("paginationBarNumbers"))
                .andExpect(model().attribute("searchType", SearchType.HASHTAG));
        then(articleService).should().searchArticlesViaHashtag(any(Pageable.class), eq(hashtag));
        then(paginationService).should()
                .getPaginationBarNumbers(
                        anyInt(),
                        anyInt()
                );
        then(articleService).should().getHashtags();

    }
}