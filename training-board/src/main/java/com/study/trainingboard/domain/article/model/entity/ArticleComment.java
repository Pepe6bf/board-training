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
        name = "article_comment",
        indexes = {
                @Index(columnList = "createdAt"),
                @Index(columnList = "createdBy")
        }
)
@Entity
public class ArticleComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 댓글 본문

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Article article;    // 연관 게시글

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount userAccount;


    private ArticleComment(
            String content,
            Article article,
            UserAccount userAccount
    ) {
        this.content = content;
        this.article = article;
        this.userAccount = userAccount;
    }

    public static ArticleComment of(
            String content,
            Article article,
            UserAccount userAccount
    ) {
        return new ArticleComment(
                content,
                article,
                userAccount
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
