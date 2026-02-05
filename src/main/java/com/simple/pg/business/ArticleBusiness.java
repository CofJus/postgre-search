package com.simple.pg.business;

import com.simple.pg.common.PageResult;
import com.simple.pg.common.Result;
import com.simple.pg.data.model.VectorizedText;
import com.simple.pg.data.request.*;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.service.ArticleService;
import com.simple.pg.utils.ChineseSegmentUtil;
import com.simple.pg.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/28
 */
@Service
public class ArticleBusiness {

    @Autowired
    private ArticleService articleService;

    public Result<Void> create(CreateArticleRequest request) {
        long articleId = SnowFlakeUtil.getInstance().nextId();
        // vectorize the title
        List<String> titleKeywords = ChineseSegmentUtil.segment(request.getTitle());
        String titleTsv = ChineseSegmentUtil.toTsVectorString(titleKeywords);
        VectorizedText titleVector = VectorizedText.of(titleKeywords, titleTsv);

        // vectorize the content
        List<String> contentKeywords = ChineseSegmentUtil.segment(request.getContent());
        String contentTsv = ChineseSegmentUtil.toTsVectorString(contentKeywords);
        VectorizedText contentVector = VectorizedText.of(contentKeywords, contentTsv);

        return articleService.create(articleId, request, titleVector, contentVector);
    }

    public Result<ArticleEntity> getByArticleId(Long articleId) {
        return articleService.getByArticleId(articleId);
    }

    public PageResult<ArticleEntity> getPage(QueryArticleRequest request) {
        return articleService.getPage(request.getPage(), request.getPageSize());
    }

    public Result<Void> update(UpdateArticleRequest request) {
        // vectorize the title
        List<String> titleKeywords = ChineseSegmentUtil.segment(request.getTitle());
        String titleTsv = ChineseSegmentUtil.toTsVectorString(titleKeywords);
        VectorizedText titleVector = VectorizedText.of(titleKeywords, titleTsv);

        // vectorize the content
        List<String> contentKeywords = ChineseSegmentUtil.segment(request.getContent());
        String contentTsv = ChineseSegmentUtil.toTsVectorString(contentKeywords);
        VectorizedText contentVector = VectorizedText.of(contentKeywords, contentTsv);

        return articleService.update(request, titleVector, contentVector);
    }

    public Result<Void> delete(DeleteArticleRequest request) {
        return articleService.deleteByArticleId(request.getArticleId());
    }
}
