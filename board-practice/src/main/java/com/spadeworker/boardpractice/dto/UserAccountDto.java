package com.spadeworker.boardpractice.dto;

import com.spadeworker.boardpractice.domain.UserAccount;

import java.time.LocalDateTime;

public record UserAccountDto(
        Long id,
        String userId,
        String email,
        String nickname,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static UserAccountDto of(Long id, String userId, String email, String nickname, String memo, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new UserAccountDto(id, userId, email, nickname, memo, createdAt, modifiedAt);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getId(),
                entity.getUserId(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                email,
                nickname,
                memo
        );
    }

}