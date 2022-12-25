package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.UserAccount;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserAccountDto {
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    private UserAccountDto(
            String email,
            String password,
            String  nickname,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static UserAccountDto of(
            String email,
            String password,
            String nickname
    ) {
        return new UserAccountDto(
                email,
                password,
                nickname,
                null,
                null,
                null,
                null
        );
    }

    public static UserAccountDto of(
            String email,
            String password,
            String nickname,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new UserAccountDto(
                email,
                password,
                nickname,
                createdAt,
                createdBy,
                updatedAt,
                updatedBy
        );
    }

    public static UserAccountDto from(UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getEmail(),
                userAccount.getPassword(),
                userAccount.getNickname(),
                userAccount.getCreatedAt(),
                userAccount.getCreatedBy(),
                userAccount.getUpdatedAt(),
                userAccount.getUpdatedBy()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                email,
                password,
                nickname
        );
    }
}
