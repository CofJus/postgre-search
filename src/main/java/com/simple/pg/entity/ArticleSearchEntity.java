package com.simple.pg.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Data
@Entity
@Table(name = "article_search")
public class ArticleSearchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id", unique = true, nullable = false)
    private Long articleId;

    private String keywords;

    @Column(columnDefinition = "tsvector", nullable = false)
    private String tsv;

    @Column(name = "update_time")
    private LocalDateTime updateTime = LocalDateTime.now();
}
