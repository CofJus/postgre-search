package com.simple.pg.repo;

import com.simple.pg.entity.ArticleSearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
public interface ArticleSearchRepository extends JpaRepository<ArticleSearchEntity, Long> {

    @Modifying
    @Query(value = "UPDATE article_search SET keywords = :keywords, tsv = CAST(:tsVectorStr AS tsvector), update_time = CURRENT_TIMESTAMP WHERE article_id = :articleId", nativeQuery = true)
    void updateByArticleId(@Param("articleId") Long articleId, @Param("keywords") String keywords, @Param("tsVectorStr") String tsVectorStr);

    @Query(value = "SELECT article_id FROM article_search WHERE tsv @@ CAST(:tsQueryStr AS tsquery) ORDER BY ts_rank(tsv, CAST(:tsQueryStr AS tsquery)) DESC", nativeQuery = true)
    List<Long> searchArticleIds(@Param("tsQueryStr") String tsQueryStr);
}