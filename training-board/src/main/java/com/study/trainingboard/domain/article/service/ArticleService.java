package com.study.trainingboard.domain.article.service;

import com.study.trainingboard.domain.article.dto.ArticleDto;
import com.study.trainingboard.domain.article.dto.ArticleWithCommentsDto;
import com.study.trainingboard.domain.article.model.constant.SearchType;
import com.study.trainingboard.domain.article.model.entity.Article;
import com.study.trainingboard.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

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
    public ArticleWithCommentsDto getArticle(Long articleId) {

        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시글입니다. - articleId : " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try {
            Article savedArticle = articleRepository.getReferenceById(dto.getId());
            savedArticle.update(
                    dto.getTitle(),
                    dto.getContent(),
                    dto.getHashtag()
            );
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto : {}", dto);
        }
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
