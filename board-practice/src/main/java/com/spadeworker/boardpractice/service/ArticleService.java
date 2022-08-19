package com.spadeworker.boardpractice.service;

import com.spadeworker.boardpractice.domain.Article;
import com.spadeworker.boardpractice.domain.SearchType;
import com.spadeworker.boardpractice.dto.ArticleCommentDto;
import com.spadeworker.boardpractice.dto.ArticleDto;
import com.spadeworker.boardpractice.dto.ArticleWithCommentsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.spadeworker.boardpractice.repository.ArticleRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * 검색어 없이 게시글을 검색하면, 게시글 전체 리스트를 조회하는 비즈니스
     * 검색어에 맞춰 게시글 리스트를 조회해주는 비즈니스
     */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        // 검색어가 없다면
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID ->
                    articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME ->
                    articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            // # 2개 들어갈 수 있으므로 리펙토링 필요
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
        };
    }

    /**
     * HashTag 로 게시물 검색 비즈니스
     */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    /**
     * HashTag 리스트 반환 비즈니스
     */
    public List<String> getHashtag() {
        return articleRepository.findAllDistincHashtags();
    }

    /**
     * 게시글 단건 조회
     */
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {

        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));
    }

    /**
     * 게시글 저장
     */
    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    /**
     * 게시글 수정
     */
    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            article.update(dto.title(), dto.content(), dto.hashtag());
        } catch (EntityNotFoundException e) {
            log.error("게시글을 찾을 수 없습니다.");
        }
    }

    /**
     * 게시글 삭제
     */
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }
}
