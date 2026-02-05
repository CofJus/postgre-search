package com.simple.pg.service;

import com.simple.pg.common.Result;
import com.simple.pg.common.ResultFactory;
import com.simple.pg.data.request.CreateArticleRequest;
import com.simple.pg.data.request.UpdateArticleRequest;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.enums.ErrorCodeEnum;
import com.simple.pg.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Rui
 * @date 2026/1/29
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Result<Void> create(long articleId, CreateArticleRequest request) {
        ArticleEntity article = ArticleEntity.of(articleId, request);
        // article meta data
        articleRepository.insert(article);
        return ResultFactory.success();
    }

    public Result<ArticleEntity> getByArticleId(Long articleId) {
        ArticleEntity article = articleRepository.selectByArticleId(articleId);
        if (article == null) {
            return ResultFactory.fail(ErrorCodeEnum.ARTICLE_NOT_FOUND);
        }
        return ResultFactory.success(article);
    }

    public Result<Void> update(UpdateArticleRequest request) {
        ArticleEntity article = articleRepository.selectByArticleId(request.getArticleId());
        if (article == null) {
            return ResultFactory.fail(ErrorCodeEnum.ARTICLE_NOT_FOUND);
        }

        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setAuthor(request.getAuthor());
        article.setResource(request.getUrl());
        article.setType(request.getType());
        article.setUpdateTime(java.time.LocalDateTime.now());

        articleRepository.update(article);
        return ResultFactory.success();
    }

    public Result<Void> deleteByArticleId(Long articleId) {
        ArticleEntity article = articleRepository.selectByArticleId(articleId);
        if (article == null) {
            return ResultFactory.fail(ErrorCodeEnum.ARTICLE_NOT_FOUND);
        }

        articleRepository.deleteByArticleId(articleId);
        return ResultFactory.success();
    }
}
