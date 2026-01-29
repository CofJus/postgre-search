package com.simple.pg.repo;

import com.simple.pg.entity.ArticleEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Repository
public interface ArticleRepository {

    int insert(ArticleEntity article);

    int update(ArticleEntity article);

    ArticleEntity selectByArticleId(Long articleId);
}