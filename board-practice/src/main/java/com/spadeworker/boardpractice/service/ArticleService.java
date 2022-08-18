package com.spadeworker.boardpractice.service;

import com.spadeworker.boardpractice.dto.ArticleCommentDto;
import com.spadeworker.boardpractice.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.ArticleRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 검색어 없이 게시글을 검색하면, 게시글 전체 리스트를 조회하는 비즈니스
     */
//    @Transactional(readOnly = true)
//    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
//        // 검색어가 없다면
//        if (searchKeyword == null || searchKeyword.isBlank()) {
//            return articleRepository.findAll(pageable).map(ArticleDto::from);
//        }
//
////        switch (searchType) {
////            case TITLE -> articleRepository.findByTitle(searchKeyword, pageable).map(ArticleDto::from);
////            case CONTENT -> articleRepository.findByTitle(searchKeyword, pageable).map(ArticleDto::from);
////            case ID -> articleRepository.findByTitle(searchKeyword, pageable).map(ArticleDto::from);
////            case NICKNAME -> articleRepository.findByTitle(searchKeyword, pageable).map(ArticleDto::from);
////            case HASHTAG -> articleRepository.findByTitle(searchKeyword, pageable).map(ArticleDto::from);
////        }
//    }


    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {

    }

    public void updateArticle(ArticleUpdateDto dto) {
    }

    public void deleteArticle(Long articleId) {
    }

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }
}
