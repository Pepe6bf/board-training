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
        name = "user_account",
        indexes = {
            @Index(columnList = "email", unique = true),
            @Index(columnList = "nickname", unique = true),
            @Index(columnList = "createdAt"),
            @Index(columnList = "createdBy")
        })
@Entity
public class UserAccount extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String nickname;

    private UserAccount(
            String email,
            String password,
            String nickname
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static UserAccount of(
            String email,
            String password,
            String nickname
    ) {
        return new UserAccount(
                email,
                password,
                nickname
        );
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
