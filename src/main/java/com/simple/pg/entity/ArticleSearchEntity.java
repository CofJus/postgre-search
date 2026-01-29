package com.simple.pg.entity;

import com.simple.pg.data.model.VectorizedText;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchEntity {

    private Long id;

    private Long articleId;

    private String titleKeywords;

    private String keywords;

    private String titleTsv;

    private String tsv;

    private Integer visible;

    private LocalDateTime updateTime;

    @Nullable
    public static ArticleSearchEntity of(Long articleId, VectorizedText titleVector, VectorizedText contentVector, Integer visible) {
        if (null == titleVector || null == contentVector) {
            return null;
        }
        ArticleSearchEntity articleSearch = new ArticleSearchEntity();
        articleSearch.setArticleId(articleId);
        articleSearch.setTitleKeywords(titleVector.getKeywords());
        articleSearch.setTitleTsv(titleVector.getTsv());
        articleSearch.setKeywords(contentVector.getKeywords());
        articleSearch.setTsv(contentVector.getTsv());
        articleSearch.setVisible(visible);
        articleSearch.setUpdateTime(LocalDateTime.now());
        return articleSearch;
    }
}
