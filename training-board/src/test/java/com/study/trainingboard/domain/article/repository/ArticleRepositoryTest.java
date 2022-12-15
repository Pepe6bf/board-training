package com.study.trainingboard.domain.article.repository;

import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.global.config.audit.AuditConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Article JPA 연결 테스트")
@Import(AuditConfig.class)
@DataJpaTest
class ArticleRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public ArticleRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @Test
    @DisplayName("조회 성공 테스트")
    void select_test() throws Exception {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(500);
    }

    @Test
    @DisplayName("게시글 작성 성공 테스트")
    void insert_test() throws Exception {
        // Given
        Long previousCount = articleRepository.count();
        Article article = Article.of(
                "title",
                "content",
                "chicken boy"
        );

        // When
        Article savedArticle = articleRepository.save(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }
}