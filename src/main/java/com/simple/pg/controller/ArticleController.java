package com.simple.pg.controller;

import com.simple.pg.business.ArticleBusiness;
import com.simple.pg.common.PageResult;
import com.simple.pg.common.Result;
import com.simple.pg.data.request.*;
import com.simple.pg.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rui
 * @date 2026/1/28
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleBusiness articleBusiness;

    @PostMapping("/create")
    public Result<Void> create(@RequestBody CreateArticleRequest request) {
        return articleBusiness.create(request);
    }

    @GetMapping("/get")
    public Result<ArticleEntity> getByArticleId(@RequestParam Long articleId) {
        return articleBusiness.getByArticleId(articleId);
    }

    @GetMapping("/list")
    public PageResult<ArticleEntity> list(QueryArticleRequest request) {
        return articleBusiness.getPage(request);
    }

    @PostMapping("/update")
    public Result<Void> update(@RequestBody UpdateArticleRequest request) {
        return articleBusiness.update(request);
    }

    @PostMapping("/delete")
    public Result<Void> delete(@RequestBody DeleteArticleRequest request) {
        return articleBusiness.delete(request);
    }
}
