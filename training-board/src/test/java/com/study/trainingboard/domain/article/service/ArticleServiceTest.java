package com.study.trainingboard.domain.article.service;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.dto.ArticleWithCommentsDto;
import com.study.trainingboard.domain.article.model.constant.SearchType;
import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.domain.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.study.trainingboard.global.util.fixture.ArticleFixture.createArticle;
import static com.study.trainingboard.global.util.fixture.ArticleFixture.createArticleDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    void 게시글_검색어없이_검색_테스트() throws Exception {
        // Given
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findAll(pageable))
                .willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(pageable, null, null);

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findAll(pageable);
    }

    @Test
    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환한다.")
    void 게시글_제목_검색_테스트() throws Exception {
        // Given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByTitleContaining(
                pageable,
                searchKeyword
        )).willReturn(Page.empty());

        // When
        Page<ArticleDto> articles = sut.searchArticles(
                pageable,
                searchType,
                searchKeyword
        );

        // Then
        assertThat(articles).isEmpty();
        then(articleRepository).should().findByTitleContaining(
                pageable,
                searchKeyword
        );
    }

    @Test
    @DisplayName("검색어 없이 게시글을 해시태그 검색하면, 빈 페이지를 반환한다.")
    void 게시글검색어_없이_해시태그_검색_테스트() throws Exception {
        // Given
        Pageable pageable = Pageable.ofSize(20);

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(
                pageable,
                null
        );

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("게시글을 해시태그 검색하면, 게시글 페이지를 반환한다.")
    void 해시태그_검색_테스트() throws Exception {
        // Given
        String hashtag = "#java";
        Pageable pageable = Pageable.ofSize(20);
        given(articleRepository.findByHashtag(pageable, hashtag))
                .willReturn(Page.empty(pageable));

        // When
        Page<ArticleDto> articles = sut.searchArticlesViaHashtag(
                pageable,
                hashtag
        );

        // Then
        assertThat(articles).isEqualTo(Page.empty(pageable));
        then(articleRepository).should().findByHashtag(pageable, hashtag);
    }

    @Test
    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    void 게시글_조회_테스트() throws Exception {
        // Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(article));

        // When
        ArticleWithCommentsDto dto = sut.getArticle(articleId);

        // Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtag", article.getHashtag());
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("없는 게시글을 조회하면, 예외를 던진다.")
    void 존재하지않는게시글_예외처리_테스트() throws Exception {
        // Given
        Long articleId = -1L;
        given(articleRepository.findById(articleId))
                .willReturn(Optional.empty());

        // When
        Throwable t = catchThrowable(() -> sut.getArticle(articleId));

        // Then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 게시글입니다. - articleId : " + articleId);
        then(articleRepository).should().findById(articleId);
    }

    @Test
    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    void 게시글_생성_테스트() throws Exception {
        // Given
        ArticleDto dto = createArticleDto();
        given(articleRepository.save(any(Article.class)))
                .willReturn(createArticle());

        // When
        sut.saveArticle(dto);

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글 id와 수정 정보를 입력하면, 게시글을 수정한다.")
    void 게시글_수정_테스트() throws Exception {
        // Given
        Article article = createArticle();
        ArticleDto articleDto = createArticleDto(
                "new title",
                "new content",
                "#springboot"
        );
        given(articleRepository.getReferenceById(articleDto.getId()))
                .willReturn(article);

        // When
        sut.updateArticle(articleDto);

        // Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", articleDto.getTitle())
                .hasFieldOrPropertyWithValue("content", articleDto.getContent())
                .hasFieldOrPropertyWithValue("hashtag", articleDto.getHashtag());
        then(articleRepository).should().getReferenceById(articleDto.getId());
    }

    @Test
    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그를 찍고 아무 것도 하지 않는다.")
    void 존재하지않는게시글_수정_예외_테스트() throws Exception {
        // Given
        ArticleDto articleDto = createArticleDto(
                "new title",
                "new content",
                "#springboot"
        );
        given(articleRepository.getReferenceById(articleDto.getId()))
                .willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticle(articleDto);

        // Then
        then(articleRepository).should().getReferenceById(articleDto.getId());
    }

    @Test
    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    void 게시글_삭제_테스트() throws Exception {
        // Given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).deleteById(articleId);

        // When
        sut.deleteArticle(articleId);

        // Then
        then(articleRepository).should().deleteById(articleId);
    }

    @DisplayName("게시글 수를 조회하면, 게시글 수를 반환한다.")
    @Test
    void 게시글수_조회_테스트() throws Exception {
        // Given
        Long expected = 0L;
        given(articleRepository.count())
                .willReturn(expected);

        // When
        Long actual = sut.getArticleCount();

        // Then
        assertThat(actual).isEqualTo(expected);
        then(articleRepository).should().count();
    }

    @DisplayName("해시태그를 조회하면, 유니크 해시태그 리스트를 반환한다")
    @Test        
    void 해시태그_조회시_유니크해시태그리스트_반환_테스트() throws Exception {
        // Given
        List<String> expectedHashtags = List.of("#java", "#spring", "#boot");
        given(articleRepository.findAllDistinctHashtags())
                .willReturn(expectedHashtags);

        // When
        List<String> actualHashtags = sut.getHashtags();
        
        // Then
        assertThat(actualHashtags).isEqualTo(expectedHashtags);
        then(articleRepository).should().findAllDistinctHashtags();
    }
}