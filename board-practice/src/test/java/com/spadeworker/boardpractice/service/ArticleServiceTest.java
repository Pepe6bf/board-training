//package com.spadeworker.boardpractice.service;
//
//import com.spadeworker.boardpractice.domain.Article;
//import com.spadeworker.boardpractice.domain.SearchType;
//import com.spadeworker.boardpractice.dto.ArticleDto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import repository.ArticleRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.catchThrowable;
//import static org.mockito.BDDMockito.*;
//
//@DisplayName("비즈니스 로직 - 게시글")
//@ExtendWith(MockitoExtension.class)
//class ArticleServiceTest {
//
//    @InjectMocks
//    private ArticleService sut;
//
//    @Mock
//    private ArticleRepository articleRepository;
//
//    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
//    @Test
//    void 검색어_없이_게시글_검색() {
//        // given
//        Pageable pageable = Pageable.ofSize(20);
//        given(articleRepository.findAll(pageable)).willReturn(Page.empty());
//
//        // When
//        Page<ArticleDto> articles = sut.searchArticles(null, null, pageable);
//
//        // Then
//        assertThat(articles).isEmpty();
//        then(articleRepository).should().findAll(pageable);
//    }
//
//    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
//    @Test
//    void 검색어추가_게시글검색() throws Exception {
//        // Given
//        SearchType searchType = SearchType.TITLE;
//        String searchKeyword = "title";
//        Pageable pageable = Pageable.ofSize(20);
//        given(articleRepository.findByTitle(searchKeyword, pageable));
//
//        // When
//        Page<ArticleDto> articles = sut.searchArticles(searchType, searchKeyword, pageable);
//
//        // Then
//        assertThat(articles).isEmpty();
//        then(articleRepository).should().findByTitle(searchKeyword, pageable);
//    }
//
//    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
//    @Test
//    void 조회_테스트() throws Exception {
//        // Given
//
//        // When
//        ArticleDto article = sut.getArticle(1L);  // 제목, 본문 ID, 닉네임, HashTag
//
//        // Then
//        assertThat(article).isNotNull();
//    }
//
//    @DisplayName("없는 게시글을 조회하면, 예외를 던진다.")
//    @Test
//    void givenNonexistentArticleId_whenSearchingArticle_thenThrowsException() {
//        // Given
//        Long articleId = 0L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.empty());
//
//        // When
//        Throwable t = catchThrowable(() -> sut.getArticle(articleId));
//
//        // Then
//        assertThat(t)
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("게시글이 없습니다 - articleId: " + articleId);
//        then(articleRepository).should().findById(articleId);
//    }
//
//    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
//    @Test
//    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
//        // Given
//        ArticleDto dto = createArticleDto();
//        given(articleRepository.save(any(Article.class))).willReturn(createArticle());
//
//        // When
//        sut.saveArticle(dto);
//
//        // Then
//        then(articleRepository).should().save(any(Article.class));
//    }
//
//    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
//    @Test
//    void givenModifiedArticleInfo_whenUpdatingArticle_thenUpdatesArticle() {
//        // Given
//        Article article = createArticle();
//        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
//        given(articleRepository.getReferenceById(dto.id())).willReturn(article);
//
//        // When
//        sut.updateArticle(dto);
//
//        // Then
//        assertThat(article)
//                .hasFieldOrPropertyWithValue("title", dto.title())
//                .hasFieldOrPropertyWithValue("content", dto.content())
//                .hasFieldOrPropertyWithValue("hashtag", dto.hashtag());
//        then(articleRepository).should().getReferenceById(dto.id());
//    }
//
//    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
//    @Test
//    void givenNonexistentArticleInfo_whenUpdatingArticle_thenLogsWarningAndDoesNothing() {
//        // Given
//        ArticleDto dto = createArticleDto("새 타이틀", "새 내용", "#springboot");
//        given(articleRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);
//
//        // When
//        sut.updateArticle(dto);
//
//        // Then
//        then(articleRepository).should().getReferenceById(dto.id());
//    }
//
//    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
//    @Test
//    void givenArticleId_whenDeletingArticle_thenDeletesArticle() {
//        // Given
//        Long articleId = 1L;
//        willDoNothing().given(articleRepository).deleteById(articleId);
//
//        // When
//        sut.deleteArticle(1L);
//
//        // Then
//        then(articleRepository).should().deleteById(articleId);
//    }
//
//
//    private UserAccount createUserAccount() {
//        return UserAccount.of(
//                "uno",
//                "password",
//                "uno@email.com",
//                "Uno",
//                null
//        );
//    }
//
//    private Article createArticle() {
//        return Article.of(
//                createUserAccount(),
//                "title",
//                "content",
//                "#java"
//        );
//    }
//
//    private ArticleDto createArticleDto() {
//        return createArticleDto("title", "content", "#java");
//    }
//
//    private ArticleDto createArticleDto(String title, String content, String hashtag) {
//        return ArticleDto.of(1L,
//                createUserAccountDto(),
//                title,
//                content,
//                hashtag,
//                LocalDateTime.now(),
//                "Uno",
//                LocalDateTime.now(),
//                "Uno");
//    }
//
//    private UserAccountDto createUserAccountDto() {
//        return UserAccountDto.of(
//                1L,
//                "uno",
//                "password",
//                "uno@mail.com",
//                "Uno",
//                "This is memo",
//                LocalDateTime.now(),
//                "uno",
//                LocalDateTime.now(),
//                "uno"
//        );
//    }
//
//}