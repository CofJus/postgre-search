package com.simple.pg.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rui
 * @date 2026/2/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryArticleRequest {

    private Long articleId;

    private Integer page = 1;

    private Integer pageSize = 10;
}
