package com.study.trainingboard.domain.article.service;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.dto.ArticleWithCommentsDto;
import com.study.trainingboard.domain.article.model.constant.SearchType;
import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.domain.article.model.entity.UserAccount;
import com.study.trainingboard.domain.article.repository.ArticleRepository;
import com.study.trainingboard.domain.article.repository.UserAccountRepository;
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

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(
            Pageable pageable,
            SearchType searchType,
            String searchKeyword
    ) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository
                    .findAll(pageable)
                    .map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository
                    .findByTitleContaining(pageable, searchKeyword)
                    .map(ArticleDto::from);
            case CONTENT -> articleRepository
                    .findByContentContaining(pageable, searchKeyword)
                    .map(ArticleDto::from);
            case EMAIL -> articleRepository
                    .findByUserAccount_EmailContaining(pageable, searchKeyword)
                    .map(ArticleDto::from);
            case NICKNAME -> articleRepository
                    .findByUserAccount_NicknameContaining(pageable, searchKeyword)
                    .map(ArticleDto::from);
            case HASHTAG -> articleRepository
                    .findByHashtag(pageable, "#" + searchKeyword)
                    .map(ArticleDto::from);
        };
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long articleId) {

        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다. - articleId : " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.findByEmail(dto.userAccountDto().email()).get();
        articleRepository.save(dto.toEntity(userAccount));
    }

    public void updateArticle(
            Long articleId,
            ArticleDto dto
    ) {
        try {
            Article savedArticle = articleRepository.getReferenceById(articleId);
            UserAccount userAccount = userAccountRepository.findByEmail(dto.userAccountDto().email())
                    .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

            if (savedArticle.getUserAccount().equals(userAccount)) {
                savedArticle.update(
                        dto.title(),
                        dto.content(),
                        dto.hashtag()
                );
            }
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 수정하는데 필요한 정보를 찾을 수 없습니다. - dto : {}", dto);
        }
    }

    public void deleteArticle(Long articleId, String userEmail) {
        articleRepository.deleteByIdAndUserAccount_Email(articleId, userEmail);
    }

    public Long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(Pageable pageable, String hashtag) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(pageable, hashtag)
                .map(ArticleDto::from);
    }


    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
