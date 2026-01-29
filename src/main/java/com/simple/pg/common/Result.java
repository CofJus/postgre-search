package com.simple.pg.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rui
 * @date 2026/1/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private T data;

    private boolean success;

    private String message;

    private int code;
}
