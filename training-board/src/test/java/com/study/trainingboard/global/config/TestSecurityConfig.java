package com.study.trainingboard.global.config;

import com.study.trainingboard.domain.article.model.entity.UserAccount;
import com.study.trainingboard.domain.article.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean
    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findByEmail(anyString()))
                .willReturn(Optional.of(UserAccount.of(
                        "pepe@email.com",
                        "pepePw1234!",
                        "pepe"
                )));
    }
}
