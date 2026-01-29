package com.simple.pg.service;

import com.simple.pg.data.model.VectorizedText;
import com.simple.pg.data.request.CreateArticleRequest;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.entity.ArticleSearchEntity;
import com.simple.pg.repo.ArticleRepository;
import com.simple.pg.repo.ArticleSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void create(long articleId, CreateArticleRequest request, VectorizedText vectorizedText) {
        ArticleEntity article = ArticleEntity.of(articleId, request);
        ArticleSearchEntity articleSearch = ArticleSearchEntity.of(articleId, vectorizedText);
        // article meta data
        articleRepository.insert(article);
        // vector data
        if (null != articleSearch) {
            articleSearchRepository.insert(articleSearch);
        }
    }
}
