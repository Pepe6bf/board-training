package com.study.trainingboard.global.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {

        // TODO: Spring Security 로 인증 기능을 도입할 때 수정할 것
        return () -> Optional.of("tester");
    }
}
