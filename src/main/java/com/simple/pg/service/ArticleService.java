package com.simple.pg.service;

import com.simple.pg.common.PageResult;
import com.simple.pg.common.PageResultFactory;
import com.simple.pg.common.Result;
import com.simple.pg.common.ResultFactory;
import com.simple.pg.data.model.VectorizedText;
import com.simple.pg.data.request.CreateArticleRequest;
import com.simple.pg.data.request.UpdateArticleRequest;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.entity.ArticleSearchEntity;
import com.simple.pg.enums.ErrorCodeEnum;
import com.simple.pg.enums.SearchVisibleStatusEnum;
import com.simple.pg.repo.ArticleRepository;
import com.simple.pg.repo.ArticleSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/29
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Transactional
    public Result<Void> create(long articleId, CreateArticleRequest request, VectorizedText titleVector, VectorizedText contentVector) {
        ArticleEntity article = ArticleEntity.of(articleId, request);
        ArticleSearchEntity articleSearch = ArticleSearchEntity.of(articleId,
                titleVector, contentVector, SearchVisibleStatusEnum.VISIBLE.getStatus());
        // article meta data
        articleRepository.insert(article);
        // vector data
        if (null != articleSearch) {
            articleSearchRepository.insert(articleSearch);
        }
        return ResultFactory.success();
    }

    public Result<ArticleEntity> getByArticleId(Long articleId) {
        ArticleEntity article = articleRepository.selectByArticleId(articleId);
        if (article == null) {
            return ResultFactory.fail(ErrorCodeEnum.ARTICLE_NOT_FOUND);
        }
        return ResultFactory.success(article);
    }

    public PageResult<ArticleEntity> getPage(int page, int pageSize) {
        long total = articleRepository.countTotal();
        if (total == 0) {
            return PageResultFactory.success(List.of(), 0);
        }
        int offset = (page - 1) * pageSize;
        List<ArticleEntity> articles = articleRepository.selectByPage(offset, pageSize);
        return PageResultFactory.success(articles, total);
    }

    @Transactional
    public Result<Void> update(UpdateArticleRequest request, VectorizedText titleVector, VectorizedText contentVector) {
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

        if (titleVector != null || contentVector != null) {
            ArticleSearchEntity searchEntity = articleSearchRepository.selectByArticleId(request.getArticleId());
            if (searchEntity != null) {
                if (titleVector != null) {
                    searchEntity.setTitleTsv(titleVector.getTsv());
                    searchEntity.setTitleKeywords(titleVector.getKeywords());
                }
                if (contentVector != null) {
                    searchEntity.setTsv(contentVector.getTsv());
                    searchEntity.setKeywords(contentVector.getKeywords());
                }
                articleSearchRepository.update(searchEntity);
            }
        }

        return ResultFactory.success();
    }

    @Transactional
    public Result<Void> deleteByArticleId(Long articleId) {
        ArticleEntity article = articleRepository.selectByArticleId(articleId);
        if (article == null) {
            return ResultFactory.fail(ErrorCodeEnum.ARTICLE_NOT_FOUND);
        }

        articleSearchRepository.deleteByArticleId(articleId);
        articleRepository.deleteByArticleId(articleId);
        return ResultFactory.success();
    }
}
