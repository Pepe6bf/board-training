package repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.spadeworker.boardpractice.domain.Article;
import com.spadeworker.boardpractice.domain.QArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle> {

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // listing 하지 않은 프로퍼티는 검색에서 제외
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.hashtag, root.createdAt, root.createdBy);
        // like '%{v}%' 쿼리 생성
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        // 시 분 초를 넣어야 해서 편리하지 않다. 차후에 개선 필요
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
