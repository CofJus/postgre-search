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

    private String keywords;

    private String tsv;

    private LocalDateTime updateTime;

    @Nullable
    public static ArticleSearchEntity of(Long articleId, VectorizedText vectorizedText) {
        if (null == vectorizedText) {
            return null;
        }
        ArticleSearchEntity articleSearch = new ArticleSearchEntity();
        articleSearch.setArticleId(articleId);
        articleSearch.setKeywords(vectorizedText.getKeywords());
        articleSearch.setTsv(vectorizedText.getTsv());
        articleSearch.setUpdateTime(LocalDateTime.now());
        return articleSearch;
    }
}
