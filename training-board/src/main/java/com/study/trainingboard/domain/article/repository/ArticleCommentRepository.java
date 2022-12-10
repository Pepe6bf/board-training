package com.study.trainingboard.domain.article.repository;

import com.study.trainingboard.domain.article.model.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
