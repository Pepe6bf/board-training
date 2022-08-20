package com.spadeworker.boardpractice.domain;

import com.spadeworker.boardpractice.config.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table()
@Entity
public class Article extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;   // 제목

    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @Column
    private String hashtag; // 해시태그

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserAccount userAccount;

    @OrderBy("id")
    // 양방향 매핑은 늘 고민해야한다.
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 게시글이 삭제되면 댓글도 같이 삭제됨
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    public Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    public void update(String title, String content, String hashtag) {
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
