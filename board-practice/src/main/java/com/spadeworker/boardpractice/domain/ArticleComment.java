package com.spadeworker.boardpractice.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class ArticleComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content; // 본문

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Article article;   // 게시글 (ID)

    @Column(nullable = false)
    @CreatedDate private LocalDateTime createdAt;

    @Column(nullable = false, length = 100)
    @CreatedBy private String createdBy;

    @Column(nullable = false)
    @LastModifiedDate private LocalDateTime modifiedAt;

    @Column(nullable = false, length = 100)
    @LastModifiedBy private String modifiedBy;

    @Builder
    public ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    // pattern matching 방식으로 리펙토링
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment articleComment)) return false;
        // 현재 entity 가 영속화 되었는지 체크
        return id != null && id.equals(articleComment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
