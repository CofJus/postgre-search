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
public class ArticleSearchEntity {

    private Long id;

    private Long articleId;

    private String keywords;

    private String tsv;

    private LocalDateTime updateTime;
}
