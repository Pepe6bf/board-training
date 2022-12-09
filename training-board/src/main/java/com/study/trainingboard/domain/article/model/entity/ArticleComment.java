package com.study.trainingboard.domain.article.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_comment")
@Entity
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 댓글 본문

    // TODO: meta data column 추가 하기(Audit)
    // TODO: 게시글 entity 와 연관관계 맺어주기
}
