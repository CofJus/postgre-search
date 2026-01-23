package com.simple.pg.service;

import com.simple.pg.common.PageResult;
import com.simple.pg.common.PageResultFactory;
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

    public PageResult<Long> search(String keyword, int page, int pageSize) {
        String tsQueryStr = ChineseSegmentUtil.toAccurateTsQueryString(keyword);
        long total = articleSearchRepository.countSearchTotal(tsQueryStr);
        if (total == 0) {
            return PageResultFactory.success(Collections.emptyList(), 0);
        }
        long offset = (long) (page - 1) * pageSize;
        List<Long> articleIds = articleSearchRepository.searchArticleIds(tsQueryStr, pageSize, offset);
        return PageResultFactory.success(articleIds, total);
    }
}
