package com.spadeworker.boardpractice.service;

import com.spadeworker.boardpractice.domain.Article;
import com.spadeworker.boardpractice.domain.constant.SearchType;
import com.spadeworker.boardpractice.domain.UserAccount;
import com.spadeworker.boardpractice.dto.ArticleCommentDto;
import com.spadeworker.boardpractice.dto.ArticleDto;
import com.spadeworker.boardpractice.dto.ArticleWithCommentsDto;
import com.spadeworker.boardpractice.repository.ArticleRepository;
import com.spadeworker.boardpractice.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

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
     * 게시글과 관련 댓글 함께 조회
     */
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    /**
     * 게시글 단건 조회
     */
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    /**
     * 게시글 저장
     */
    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().id());
        articleRepository.save(dto.toEntity(userAccount));
    }

    /**
     * 게시글 수정
     */
    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().id());

            if (article.getUserAccount().equals(userAccount)) {
                article.update(dto.title(), dto.content(), dto.hashtag());
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    /**
     * 게시글 삭제
     */
    public void deleteArticle(Long articleId, String userId) {
        articleRepository.deleteByIdAndUserAccount_UserId(articleId, userId);
    }

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }
}
