package com.simple.pg.controller;

import com.simple.pg.business.SearchBusiness;
import com.simple.pg.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rui
 * @date 2026/1/23
 */
@RequestMapping("/search")
@RestController
public class SearchController {

    @Autowired
    private SearchBusiness searchBusiness;

    @GetMapping("/result")
    public PageResult<Long> search(@RequestParam(name = "keyword") String keyword,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        return searchBusiness.search(keyword, page, pageSize);
    }
}
