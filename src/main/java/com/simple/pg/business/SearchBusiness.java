package com.simple.pg.business;

import com.simple.pg.common.PageResult;
import com.simple.pg.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rui
 * @date 2026/1/23
 */
@Service
public class SearchBusiness {

    @Autowired
    private ArticleSearchService articleSearchService;

    public PageResult<Long> search(String keyword, int page, int pageSize) {

        return articleSearchService.search(keyword, page, pageSize);
    }
}
