package com.study.trainingboard.domain.article.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.domain.article.model.entity.QArticle;
import com.study.trainingboard.domain.article.repository.querydsl.ArticleRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {

    Page<Article> findByTitleContaining(Pageable pageable, String title);
    Page<Article> findByContentContaining(Pageable pageable, String content);
    Page<Article> findByUserAccount_EmailContaining(Pageable pageable, String email);
    Page<Article> findByUserAccount_NicknameContaining(Pageable pageable, String nickname);
    Page<Article> findByHashtag(Pageable pageable, String hashtag);

    void deleteByIdAndUserAccount_Email(Long articleId, String email);

    @Override
    default void customize(
            QuerydslBindings bindings,
            QArticle root
    ) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(
                root.title,
                root.content,
                root.hashtag,
                root.createdAt,
                root.createdBy
        );
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
