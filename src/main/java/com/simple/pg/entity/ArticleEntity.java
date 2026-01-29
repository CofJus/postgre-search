package com.simple.pg.entity;

import com.simple.pg.data.request.CreateArticleRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {

    private Long id;

    private Long articleId;
    private String title;
    private String content;
    private String author;
    private String resource;
    private Integer type;
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static ArticleEntity of(long articleId, CreateArticleRequest request) {
        ArticleEntity article = new ArticleEntity();
        article.setArticleId(articleId);
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setAuthor(request.getAuthor());
        article.setResource(request.getUrl());
        article.setType(request.getType());
        article.setStatus(1);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        return article;
    }
}
