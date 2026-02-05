package com.simple.pg.service;

import com.simple.pg.common.PageResult;
import com.simple.pg.common.PageResultFactory;
import com.simple.pg.data.model.VectorizedText;
import com.simple.pg.data.request.CreateArticleRequest;
import com.simple.pg.data.request.UpdateArticleRequest;
import com.simple.pg.entity.ArticleEntity;
import com.simple.pg.entity.ArticleSearchEntity;
import com.simple.pg.enums.SearchVisibleStatusEnum;
import com.simple.pg.repo.ArticleRepository;
import com.simple.pg.repo.ArticleSearchRepository;
import com.simple.pg.utils.ChineseSegmentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Service
@Slf4j
public class ArticleSearchService {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public PageResult<ArticleEntity> search(String keyword, int page, int pageSize) {
        String tsQueryStr = ChineseSegmentUtil.toAccurateTsQueryString(keyword);
        long total = articleSearchRepository.countSearchTotal(tsQueryStr, keyword.length() == 1 ? 1 : 0);
        if (total == 0) {
            return PageResultFactory.success(Collections.emptyList(), 0);
        }
        long offset = (long) (page - 1) * pageSize;
        List<Long> articleIds = articleSearchRepository.search(tsQueryStr, keyword.length() == 1 ? 1 : 0, pageSize, offset);
        List<ArticleEntity> articles = articleRepository.selectByArticleIds(articleIds);
        return PageResultFactory.success(articles, total);
    }

    /**
     * Async processing for create: segment, vectorize, and insert into article_search.
     */
    @Async
    public void asyncProcessCreate(long articleId, CreateArticleRequest request) {
        try {
            List<String> titleKeywords = ChineseSegmentUtil.segment(request.getTitle());
            String titleTsv = ChineseSegmentUtil.toTsVectorString(titleKeywords);
            VectorizedText titleVector = VectorizedText.of(titleKeywords, titleTsv);

            List<String> contentKeywords = ChineseSegmentUtil.segment(request.getContent());
            String contentTsv = ChineseSegmentUtil.toTsVectorString(contentKeywords);
            VectorizedText contentVector = VectorizedText.of(contentKeywords, contentTsv);

            ArticleSearchEntity articleSearch = ArticleSearchEntity.of(articleId,
                    titleVector, contentVector, SearchVisibleStatusEnum.VISIBLE.getStatus());
            if (articleSearch != null) {
                articleSearchRepository.insert(articleSearch);
            }
        } catch (Exception e) {
            log.error("Async processCreate failed for articleId={}", articleId, e);
        }
    }

        /**
     * Async processing for update: recompute vectors and update article_search.
     */
    @Async
    public void asyncProcessUpdate(UpdateArticleRequest request) {
        try {
            List<String> titleKeywords = ChineseSegmentUtil.segment(request.getTitle());
            String titleTsv = ChineseSegmentUtil.toTsVectorString(titleKeywords);
            List<String> contentKeywords = ChineseSegmentUtil.segment(request.getContent());
            String contentTsv = ChineseSegmentUtil.toTsVectorString(contentKeywords);

            articleSearchRepository.updateVectorByArticleId(
                    request.getArticleId(),
                    String.join(",", contentKeywords),
                    String.join(",", titleKeywords),
                    contentTsv,
                    titleTsv
            );
        } catch (Exception e) {
            log.error("Async processUpdate failed for articleId={}", request.getArticleId(), e);
        }
    }

    /**
     * Async processing for vector deletion to keep search table consistent.
     */
    @Async
    public void asyncProcessDelete(Long articleId) {
        try {
            articleSearchRepository.deleteByArticleId(articleId);
        } catch (Exception e) {
            log.error("Async processDeleteVectors failed for articleId={}", articleId, e);
        }
    }

}
