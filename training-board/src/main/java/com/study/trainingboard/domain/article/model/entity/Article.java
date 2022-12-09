package com.study.trainingboard.domain.article.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "article",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "content"),
                @Index(columnList = "hashtag"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        })
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시글 본문

    @Column
    private String hashtag;  // 게시글 해시태그

    // TODO: meta data column 추가 하기(Audit)
}
