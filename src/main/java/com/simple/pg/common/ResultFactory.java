package com.simple.pg.common;

/**
 * @author Rui
 * @date 2026/1/29
 */
public class ResultFactory {

    public static <T> Result<T> success(T data) {
        return result(0, "success", true, data);
    }

    public static <T> Result<T> success() {
        return result(0, "success", true, null);
    }

    public static <T> Result<T> fail(String message, int code) {
        return result(code, message, false, null);
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
