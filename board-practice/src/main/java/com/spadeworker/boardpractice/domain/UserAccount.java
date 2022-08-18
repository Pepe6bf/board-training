package com.spadeworker.boardpractice.domain;

import com.spadeworker.boardpractice.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(columnList = "user_id"),
        @Index(columnList = "email", unique = true),
        @Index(columnList = "created_at"),
        @Index(columnList = "created_by"),
})
@Entity
public class UserAccount extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String nickname;
    private String memo;

    @Builder
    private UserAccount(String userId, String email, String nickname, String memo) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String email, String nickname, String memo) {
        return new UserAccount(userId, email, nickname, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAccount userAccount)) return false;
        return id != null && id.equals(userAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
