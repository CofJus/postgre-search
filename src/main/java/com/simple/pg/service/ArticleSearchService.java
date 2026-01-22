package com.simple.pg.service;

import com.simple.pg.common.PageResult;
import com.simple.pg.repo.ArticleSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    public PageResult<Long> search(String keyword) {


        return null;
    }
}
