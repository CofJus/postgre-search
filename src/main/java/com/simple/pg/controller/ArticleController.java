package com.simple.pg.controller;

import com.simple.pg.business.ArticleBusiness;
import com.simple.pg.data.request.CreateArticleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void create(@RequestBody CreateArticleRequest request) {
        articleBusiness.create(request);
    }
}
