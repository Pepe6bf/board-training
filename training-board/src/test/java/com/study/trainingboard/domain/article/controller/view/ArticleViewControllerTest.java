package com.study.trainingboard.domain.article.controller.view;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.dto.request.ArticleRequest;
import com.study.trainingboard.domain.article.model.constant.FormStatus;
import com.study.trainingboard.domain.article.model.constant.SearchType;
import com.study.trainingboard.domain.article.service.ArticleService;
import com.study.trainingboard.domain.article.service.PaginationService;
import com.study.trainingboard.global.config.TestSecurityConfig;
import com.study.trainingboard.global.util.encoder.FormDataEncoder;
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
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.study.trainingboard.global.util.fixture.ArticleFixture.createArticleDto;
import static com.study.trainingboard.global.util.fixture.ArticleFixture.createArticleWithCommentsDto;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View ???????????? - ?????????")
@Import({
        TestSecurityConfig.class,
        FormDataEncoder.class
})
@WebMvcTest(ArticleViewController.class)
class ArticleViewControllerTest {

    private final MockMvc mockMvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private PaginationService paginationService;

    public ArticleViewControllerTest(
            @Autowired MockMvc mockMvc,
            @Autowired FormDataEncoder formDataEncoder
    ) {
        this.mockMvc = mockMvc;
        this.formDataEncoder = formDataEncoder;
    }

    @Test
    @DisplayName("[view][GET] ????????? ????????? (?????????) ????????? - ?????? ??????")
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
    @DisplayName("[view][GET] ????????? ????????? (?????????) ????????? - ???????????? ?????? ??????")
    void ?????????_??????_?????????() throws Exception {
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

    @DisplayName("[view][GET] ????????? ????????? (?????????) ????????? - ?????????, ?????? ??????")
    @Test
    void ?????????_?????????_?????????() throws Exception {
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

    @DisplayName("[view][GET] ????????? ????????? - ?????? ?????? ??? ????????? ???????????? ??????")
    @Test
    void ???????????????????????????_??????????????????_??????_??????_?????????() throws Exception {
        // Given
        Long articleId = 1L;

        // When & Then
        mockMvc.perform(get("/articles/" + articleId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
        then(articleService).shouldHaveNoInteractions();
    }

    @DisplayName("[view][GET] ????????? ????????? - ?????? ??????, ????????? ?????????")
    @WithMockUser
    @Test
    void view_detail_page_test() throws Exception {
        // Given
        Long articleId = 1L;
        Long totalCount = 1L;

        given(articleService.getArticleWithComments(articleId))
                .willReturn(createArticleWithCommentsDto());
        given(articleService.getArticleCount()).willReturn(totalCount);

        // When

        // Then
        mockMvc.perform(get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/detail"))
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("articleComments"))
                .andExpect(model().attribute("totalCount", totalCount));
        then(articleService).should().getArticleWithComments(articleId);
        then(articleService).should().getArticleCount();
    }

    @Disabled("?????? ???")
    @Test
    @DisplayName("[view][GET] ????????? ?????? ?????? ????????? - ?????? ??????")
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
    @DisplayName("[view][GET] ????????? ???????????? ?????? ????????? - ?????? ??????")
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
    @DisplayName("[view][GET] ????????? ???????????? ?????? ????????? - ?????? ??????, ???????????? ??????")
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

    @DisplayName("[view][GET] ??? ????????? ?????? ?????????")
    @WithMockUser
    @Test
    void givenNothing_whenRequesting_thenReturnsNewArticlePage() throws Exception {
        // Given

        // When & Then
        mockMvc.perform(get("/articles/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/form"))
                .andExpect(model().attribute("formStatus", FormStatus.CREATE));
    }

    @DisplayName("[view][POST] ??? ????????? ?????? - ?????? ??????")
    @WithUserDetails(
            value = "pepe@email.com",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @Test
    void givenNewArticleInfo_whenRequesting_thenSavesNewArticle() throws Exception {
        // Given
        ArticleRequest articleRequest = ArticleRequest.of("new title", "new content", "#new");
        willDoNothing().given(articleService).saveArticle(any(ArticleDto.class));

        // When & Then
        mockMvc.perform(
                        post("/articles/form")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(articleRequest))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"))
                .andExpect(redirectedUrl("/articles"));
        then(articleService).should().saveArticle(any(ArticleDto.class));
    }

    @DisplayName("[view][GET] ????????? ?????? ????????? - ?????? ?????? ??? ????????? ???????????? ??????")
    @Test
    void ???????????????????????????_?????????_??????_??????_?????????() throws Exception {
        // Given
        Long articleId = 1L;

        // When & Then
        mockMvc.perform(get("/articles" + articleId + "/form"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
        then(articleService).shouldHaveNoInteractions();
    }

    @DisplayName("[view][GET] ????????? ?????? ????????? - ?????? ??????, ????????? ?????????")
    @WithMockUser
    @Test
    void givenNothing_whenRequesting_thenReturnsUpdatedArticlePage() throws Exception {
        // Given
        long articleId = 1L;
        ArticleDto dto = createArticleDto();
        given(articleService.getArticle(articleId)).willReturn(dto);

        // When & Then
        mockMvc.perform(get("/articles/" + articleId + "/form"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/form"))
//                .andExpect(model().attribute("article", ArticleResponse.from(dto)))
                .andExpect(model().attribute("formStatus", FormStatus.UPDATE));
        then(articleService).should().getArticle(articleId);
    }

    @DisplayName("[view][POST] ????????? ?????? - ?????? ??????")
    @WithUserDetails(
            value = "pepe@email.com",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @Test
    void givenUpdatedArticleInfo_whenRequesting_thenUpdatesNewArticle() throws Exception {
        // Given
        long articleId = 1L;
        ArticleRequest articleRequest = ArticleRequest.of("new title", "new content", "#new");
        willDoNothing().given(articleService).updateArticle(eq(articleId), any(ArticleDto.class));

        // When & Then
        mockMvc.perform(
                        post("/articles/" + articleId + "/form")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(articleRequest))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));
        then(articleService).should().updateArticle(eq(articleId), any(ArticleDto.class));
    }

    @DisplayName("[view][POST] ????????? ?????? - ?????? ??????")
    @WithUserDetails(
            value = "pepe@email.com",
            userDetailsServiceBeanName = "userDetailsService",
            setupBefore = TestExecutionEvent.TEST_EXECUTION
    )
    @Test
    void givenArticleIdToDelete_whenRequesting_thenDeletesArticle() throws Exception {
        // Given
        long articleId = 1L;
        String userEmail = "pepe@email.com";
        willDoNothing().given(articleService).deleteArticle(articleId, userEmail);

        // When & Then
        mockMvc.perform(
                        post("/articles/" + articleId + "/delete")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles"))
                .andExpect(redirectedUrl("/articles"));
        then(articleService).should().deleteArticle(articleId, userEmail);
    }
}