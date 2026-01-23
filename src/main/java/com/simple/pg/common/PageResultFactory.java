package com.simple.pg.common;

import java.util.Collections;
import java.util.List;

/**
 * @author Rui
 * @date 2026/1/23
 */
public class PageResultFactory {

    private static <T> PageResult<T> to(List<T> itemList, long total, boolean success) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setItemList(itemList);
        pageResult.setSuccess(success);
        return pageResult;
    }

    public static <T> PageResult<T> success(List<T> itemList, long total) {
        return to(itemList, total, true);
    }

    public static <T> PageResult<T> fail() {
        return to(Collections.emptyList(), 0, false);
    }

}
