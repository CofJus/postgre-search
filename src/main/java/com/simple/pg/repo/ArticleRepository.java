package com.simple.pg.repo;

import com.simple.pg.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rui
 * @date 2026/1/22
 */
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}