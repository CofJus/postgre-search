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
public class DeleteArticleRequest {

    private Long articleId;
}
