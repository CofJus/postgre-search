package com.simple.pg.data.response;

import com.simple.pg.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Rui
 * @date 2026/2/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchResponse {

    private Long articleId;
    private String title;
    private String content;
    private String author;
    private String resource;
    private Integer type;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static ArticleSearchResponse from(ArticleEntity entity) {
        if (entity == null) {
            return null;
        }
        ArticleSearchResponse response = new ArticleSearchResponse();
        response.setArticleId(entity.getArticleId());
        response.setTitle(entity.getTitle());
        response.setContent(entity.getContent());
        response.setAuthor(entity.getAuthor());
        response.setResource(entity.getResource());
        response.setType(entity.getType());
        response.setStatus(entity.getStatus());
        response.setCreateTime(entity.getCreateTime());
        response.setUpdateTime(entity.getUpdateTime());
        return response;
    }
}

