package com.study.trainingboard.domain.article.service;

import com.study.trainingboard.domain.article.dto.ArticleCommentDto;
import com.study.trainingboard.domain.article.model.entity.ArticleComment;
import com.study.trainingboard.domain.article.repository.ArticleCommentRepository;
import com.study.trainingboard.domain.article.repository.ArticleRepository;
import com.study.trainingboard.domain.article.repository.UserAccountRepository;
import com.study.trainingboard.global.util.fixture.ArticleCommentFixture;
import com.study.trainingboard.global.util.fixture.ArticleFixture;
import com.study.trainingboard.global.util.fixture.UserAccountFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
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

    @Mock
    private UserAccountRepository userAccountRepository;

    @Test
    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    void 게시글댓글_조회_테스트() throws Exception {
        // Given
        Long articleId = 1L;
        ArticleComment expected = ArticleCommentFixture.createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId))
                .willReturn(List.of(expected));

        // When
        List<ArticleCommentDto> actual = sut.searchArticleComment(articleId);

        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }

    @Test
    @DisplayName("댓글 정보를 입력하면, 댓글을 저장한다.")
    void 게시글댓글_생성_테스트() throws Exception {
        // Given
        ArticleCommentDto dto = ArticleCommentFixture.createArticleCommentDto("Comment");
        given(articleRepository.getReferenceById(dto.articleId()))
                .willReturn(ArticleFixture.createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class)))
                .willReturn(null);
        given(userAccountRepository.findByEmail(dto.userAccountDto().getEmail()))
                .willReturn(Optional.of(UserAccountFixture.createUserAccount()));

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
        then(userAccountRepository).should().findByEmail(dto.userAccountDto().getEmail());
    }

    @Test
    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안 한다.")
    void 존재하지않는_게시글_댓글_생성_예외_테스트() throws Exception {
        // Given
        ArticleCommentDto dto = ArticleCommentFixture.createArticleCommentDto("Comment");
        given(articleRepository.getReferenceById(dto.articleId()))
                .willThrow(EntityNotFoundException.class);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).shouldHaveNoInteractions();
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다.")
    void 댓글_수정_테스트() throws Exception {
        // Given
        String oldContent = "content";
        String updatedContent = "new content";
        ArticleComment articleComment = ArticleCommentFixture.createArticleComment(oldContent);
        ArticleCommentDto dto = ArticleCommentFixture.createArticleCommentDto(updatedContent);
        given(articleCommentRepository.getReferenceById(dto.id()))
                .willReturn(articleComment);

        // When
        sut.updateArticleComment(dto);

        // Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updatedContent);
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안 한다.")
    @Test
    void 존재하지않는댓글_수정시_예외_테스트() throws Exception {
        // Given
        ArticleCommentDto dto = ArticleCommentFixture.createArticleCommentDto("comment");
        given(articleCommentRepository.getReferenceById(dto.id()))
                .willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticleComment(dto);

        // Then
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }

    @Test
    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    void 게시글댓글_삭제_테스트() throws Exception {
        // Given
        Long articleCommentId = 1L;
        String userEmail = "pepe@email.com";

        willDoNothing().given(articleCommentRepository).deleteByIdAndUserAccount_Email(articleCommentId, userEmail);

        // When
        sut.deleteArticleComment(articleCommentId, userEmail);

        // Then
        then(articleCommentRepository).should().deleteByIdAndUserAccount_Email(articleCommentId, userEmail);
    }
}