package com.simple.pg.service;

import com.simple.pg.common.PageResult;
import com.simple.pg.common.PageResultFactory;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.repo.ArticleRepository;
import com.simple.pg.repo.ArticleSearchRepository;
import com.simple.pg.utils.ChineseSegmentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public PageResult<ArticleEntity> search(String keyword, int page, int pageSize) {
        String tsQueryStr = ChineseSegmentUtil.toAccurateTsQueryString(keyword);
        long total = articleSearchRepository.countSearchTotal(tsQueryStr, keyword.length() == 1 ? 1 : 0);
        if (total == 0) {
            return PageResultFactory.success(Collections.emptyList(), 0);
        }
        long offset = (long) (page - 1) * pageSize;
        List<Long> articleIds = articleSearchRepository.search(tsQueryStr, keyword.length() == 1 ? 1 : 0, pageSize, offset);
        List<ArticleEntity> articles = articleRepository.selectByArticleIds(articleIds);
        return PageResultFactory.success(articles, total);
    }
}
