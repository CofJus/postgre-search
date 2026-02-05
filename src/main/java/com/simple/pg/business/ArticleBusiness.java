package com.simple.pg.business;

import com.simple.pg.common.Result;
import com.simple.pg.data.request.*;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.service.ArticleSearchService;
import com.simple.pg.service.ArticleService;
import com.simple.pg.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rui
 * @date 2026/1/28
 */
@Service
public class ArticleBusiness {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleSearchService articleSearchService;

    public Result<Void> create(CreateArticleRequest request) {
        long articleId = SnowFlakeUtil.getInstance().nextId();
        Result<Void> res = articleService.create(articleId, request);
        articleSearchService.asyncProcessCreate(articleId, request);
        return res;
    }

    public Result<ArticleEntity> getByArticleId(Long articleId) {
        return articleService.getByArticleId(articleId);
    }

    public Result<Void> update(UpdateArticleRequest request) {
        Result<Void> res = articleService.update(request);
        articleSearchService.asyncProcessUpdate(request);
        return res;
    }

    public Result<Void> delete(DeleteArticleRequest request) {
        Result<Void> res = articleService.deleteByArticleId(request.getArticleId());
        articleSearchService.asyncProcessDelete(request.getArticleId());
        return res;
    }
}
