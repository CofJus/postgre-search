package com.simple.pg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * file type
 * @author Rui
 * @date 2026/1/28
 */
@Getter
@AllArgsConstructor
public enum ArticleTypeEnum {

    MARKDOWN(1, "markdown"),

    ;

    private final int type;

    private final String desc;
}
