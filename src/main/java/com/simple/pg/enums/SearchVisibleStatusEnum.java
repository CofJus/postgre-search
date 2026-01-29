package com.simple.pg.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Rui
 * @date 2026/1/29
 */
@Getter
@AllArgsConstructor
public enum SearchVisibleStatusEnum {

    VISIBLE(1),

    HIDDEN(0),

    ;

    private final int status;
}
