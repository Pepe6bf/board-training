//package com.spadeworker.boardpractice.service;
//
//import com.spadeworker.boardpractice.domain.Article;
//import com.spadeworker.boardpractice.dto.ArticleCommentDto;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import repository.ArticleCommentRepository;
//import repository.ArticleRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.BDDMockito.*;
//
//@DisplayName("비즈니스 로직 - 댓글")
//@ExtendWith(MockitoExtension.class)
//class ArticleCommentDtoServiceTest {
//
//    @InjectMocks
//    private ArticleService sut;
//
//    @Mock
//    private ArticleRepository articleRepository;
//    @Mock
//    private ArticleCommentRepository articleCommentRepository;
//
//    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
//    @Test
//    void 댓글_조회_테스트() throws Exception {
//        // Given
//        Long articleId = 1L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#java")));
//
//        // When
//        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);  // 제목, 본문 ID, 닉네임, HashTag
//
//        // Then
//        assertThat(articleComments).isNotNull();
//        then(articleRepository).should().findById(articleId);
//    }
//
//    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
//    @Test
//    void 댓글_조회_테스트() throws Exception {
//        // Given
//        Long articleId = 1L;
//        given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("title", "content", "#java")));
//
//        // When
//        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);  // 제목, 본문 ID, 닉네임, HashTag
//
//        // Then
//        assertThat(articleComments).isNotNull();
//        then(articleRepository).should().findById(articleId);
//    }
//
//}