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

    int updateVectorByArticleId(@Param("articleId") Long articleId,
                          @Param("keywords") String keywords, @Param("titleKeywords") String titleKeywords,
                          @Param("tsVectorStr") String tsVectorStr, @Param("titleTsVectorStr") String titleTsVectorStr);

    ArticleSearchEntity selectByArticleId(@Param("articleId") Long articleId);

    List<Long> search(@Param("tsQueryStr") String tsQueryStr,
                                @Param("isSingleChar") int isSingleChar,
                                @Param("limit") long limit,
                                @Param("offset") long offset);

    Long countSearchTotal(@Param("tsQueryStr") String tsQueryStr, @Param("isSingleChar") int isSingleChar);
}