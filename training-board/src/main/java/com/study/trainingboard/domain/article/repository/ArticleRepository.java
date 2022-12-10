package com.study.trainingboard.domain.article.repository;

import com.study.trainingboard.domain.article.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
