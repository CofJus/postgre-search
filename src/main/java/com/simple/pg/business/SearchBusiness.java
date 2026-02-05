package com.simple.pg.business;

import com.simple.pg.common.PageResult;
import com.simple.pg.data.response.ArticleSearchResponse;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rui
 * @date 2026/1/23
 */
@Service
public class SearchBusiness {

    @Autowired
    private ArticleSearchService articleSearchService;

    public PageResult<ArticleSearchResponse> search(String keyword, int page, int pageSize) {
        PageResult<ArticleEntity> entityResult = articleSearchService.search(keyword, page, pageSize);
        List<ArticleSearchResponse> responseList = entityResult.getItemList().stream()
                .map(ArticleSearchResponse::from)
                .collect(Collectors.toList());
        return new PageResult<>(entityResult.getTotal(), responseList, entityResult.isSuccess());
    }
}
