package com.simple.pg.common;

import com.simple.pg.enums.ErrorCodeEnum;

/**
 * @author Rui
 * @date 2026/1/29
 */
public class ResultFactory {

    public static <T> Result<T> success(T data) {
        return result(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage(), true, data);
    }

    public static <T> Result<T> success() {
        return result(ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMessage(), true, null);
    }

    public static <T> Result<T> fail(ErrorCodeEnum errorCode) {
        return result(errorCode.getCode(), errorCode.getMessage(), false, null);
    }

    private static <T> Result<T> result(int code, String message, boolean success, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(success);
        result.setData(data);
        return result;
    }
}
