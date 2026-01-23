package com.simple.pg.entity;

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

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Integer status = 1;

}
