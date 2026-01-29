package com.simple.pg.business;

import com.simple.pg.data.model.VectorizedText;
import com.simple.pg.data.request.CreateArticleRequest;
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

    public void create(CreateArticleRequest request) {
        long articleId = SnowFlakeUtil.getInstance().nextId();
        List<String> keywords = ChineseSegmentUtil.segment(request.getContent());
        String tsv = ChineseSegmentUtil.toTsVectorString(keywords);
        VectorizedText vectorizedText = VectorizedText.of(keywords, tsv);
        articleService.create(articleId, request, vectorizedText);
    }

}
