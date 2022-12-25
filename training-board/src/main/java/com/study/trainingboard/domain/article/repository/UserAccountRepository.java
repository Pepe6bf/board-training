package com.study.trainingboard.domain.article.repository;

import com.study.trainingboard.domain.article.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    UserAccount findByEmail(String email);
}
