package com.spadeworker.boardpractice.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<String> findAllDistincHashtags();
}
