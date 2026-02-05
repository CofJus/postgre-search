package com.simple.pg.repo;

import com.simple.pg.entity.ArticleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Repository
public interface ArticleRepository {

    int insert(ArticleEntity article);

    int update(ArticleEntity article);

    ArticleEntity selectByArticleId(Long articleId);

    List<ArticleEntity> selectByPage(int offset, int pageSize);

    List<ArticleEntity> selectByArticleIds(List<Long> articleIds);

    int countTotal();

    int deleteByArticleId(Long articleId);
}