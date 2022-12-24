package com.study.trainingboard.domain.article.dto;

import com.study.trainingboard.domain.article.model.entity.UserAccount;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserAccountDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime updatedAt;
    private final String updatedBy;

    private UserAccountDto(
            Long id,
            String email,
            String password,
            String  nickname,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public static UserAccountDto of(
            Long id,
            String email,
            String password,
            String nickname,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime updatedAt,
            String updatedBy
    ) {
        return new UserAccountDto(
                id,
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
                userAccount.getId(),
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
