package com.simple.pg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 * @author Rui
 * @date 2026/2/5
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    SUCCESS(0, "success"),

    ARTICLE_NOT_FOUND(1001, "文章不存在"),

    ARTICLE_CREATE_FAILED(1002, "文章创建失败"),

    ARTICLE_UPDATE_FAILED(1003, "文章更新失败"),

    ARTICLE_DELETE_FAILED(1004, "文章删除失败"),

    ;

    private final int code;

    private final String message;
}
