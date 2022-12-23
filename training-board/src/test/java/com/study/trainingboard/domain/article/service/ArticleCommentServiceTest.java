package com.study.trainingboard.domain.article.service;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.domain.article.model.entity.ArticleComment;
import com.study.trainingboard.domain.article.repository.ArticleCommentRepository;
import com.study.trainingboard.domain.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


@DisplayName("비즈니스 로직 - 게시글 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks
    private ArticleCommentService sut;

    @Mock
    private ArticleCommentRepository articleCommentRepository;
    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 ID를 주면, 해당 게시글의 댓글 리스트를 반환한다.")
    void 게시글댓글_조회_테스트() throws Exception {
        // Given
        Long articleId = 1L;
        Article savedArticle = Article.of(
                "test title",
                "test content",
                "test"
        );

        given(articleRepository.findById(articleId))
                .willReturn(Optional.of(savedArticle));
        
        // When
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);
        
        // Then
        assertThat(articleComments).isNotNull();
        then(articleRepository).should().findById(articleId);
    }
    
    @Test
    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    void 게시글댓글_생성_테스트() throws Exception {
        // Given
        
        // When
        
        // Then
    }

}