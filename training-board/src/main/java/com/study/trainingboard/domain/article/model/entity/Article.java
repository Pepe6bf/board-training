package com.study.trainingboard.domain.article.model.entity;

import com.study.trainingboard.global.config.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "article",
        indexes = {
                @Index(columnList = "title"),
                @Index(columnList = "hashtag"),
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        })
@Entity
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title; // 게시글 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 게시글 본문

    @Column
    private String hashtag;  // 게시글 해시태그

    private Article(
            String title,
            String content,
            String hashtag
    ) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount userAccount;

    public static Article of(
            String title,
            String content,
            String hashtag
    ) {
        return new Article(
                title,
                content,
                hashtag
        );
    }

    // 각각의 객체를 비교하는 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
