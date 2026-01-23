package com.simple.pg.repo;

import com.simple.pg.entity.ArticleSearchEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Repository
public interface ArticleSearchRepository {

    int insert(ArticleSearchEntity articleSearch);

    int updateByArticleId(@Param("articleId") Long articleId,
                          @Param("keywords") String keywords,
                          @Param("tsVectorStr") String tsVectorStr);

    ArticleSearchEntity selectByArticleId(@Param("articleId") Long articleId);

    List<Long> searchArticleIds(@Param("tsQueryStr") String tsQueryStr,
                                @Param("limit") long limit,
                                @Param("offset") long offset);

    Long countSearchTotal(@Param("tsQueryStr") String tsQueryStr);
}