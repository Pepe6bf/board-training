package com.study.trainingboard.domain.article.service;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.dto.ArticleUpdateDto;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
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
    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    void 게시글_검색_테스트() throws Exception {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(
                SearchType.TITLE,
                "search keyword"
        );

        // Then
        assertThat(articles).isNotNull();
    }

    @Test
    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    void 게시글_조회_테스트() throws Exception {
        // Given
        Long articleId = 1L;

        // When
        ArticleDto article = sut.searchArticle(articleId);

        // Then
        assertThat(article).isNotNull();
    }

    @Test
    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다.")
    void 게시글_생성_테스트() throws Exception {
        // Given
        ArticleDto dto = ArticleDto.of(
                "Test title",
                "Test content",
                "test hashtag",
                LocalDateTime.now(),
                "pepe",
                LocalDateTime.now(),
                "pepe"
        );
        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        // When
        sut.saveArticle(dto);

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글 id와 수정 정보를 입력하면, 게시글을 수정한다.")
    void 게시글_수정_테스트() throws Exception {
        // Given
        Long articleId = 1L;
        ArticleUpdateDto dto = ArticleUpdateDto.of(
                "Test title",
                "Test content",
                "test hashtag"
        );
        given(articleRepository.save(any(Article.class)))
                .willReturn(null);

        // When
        sut.updateArticle(articleId, dto);

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @Test
    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다.")
    void 게시글_삭제_테스트() throws Exception {
        // Given
        Long articleId = 1L;
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(articleId);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }


}