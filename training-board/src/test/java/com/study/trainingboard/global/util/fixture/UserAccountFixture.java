package com.study.trainingboard.global.util.fixture;

import com.study.trainingboard.domain.article.dto.UserAccountDto;
import com.study.trainingboard.domain.article.model.entity.UserAccount;

import java.time.LocalDateTime;

public class UserAccountFixture {

    public static UserAccount createUserAccount() {
        return UserAccount.of(
                "tester@email.com",
                "testerPw1234!",
                "tester"
        );
    }

    public static UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                1L,
                "tester@email.com",
                "testerPw1234!",
                LocalDateTime.now(),
                "tester",
                LocalDateTime.now(),
                "tester"
        );
    }
}
