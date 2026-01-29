package com.simple.pg.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rui
 * @date 2026/1/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {

    private String title;

    /**
     * @see com.simple.pg.enums.ArticleTypeEnum
     */
    private int type;

    private String url;

    private String content;

    private String author;
}
