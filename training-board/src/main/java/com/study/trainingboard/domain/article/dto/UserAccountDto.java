package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        String email,
        String password,
        String nickname,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        String updatedBy
) {

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

