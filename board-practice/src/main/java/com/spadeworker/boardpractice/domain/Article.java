package com.spadeworker.boardpractice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;   // 제목

    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Column
    private String hashtag; // 해시태그

    @OrderBy("id")
    // 양방향 매핑은 늘 고민해야한다.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 게시글이 삭제되면 댓글도 같이 삭제됨
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @Builder
    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // pattern matching 방식으로 리펙토링
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        // 현재 entity 가 영속화 되었는지 체크
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
