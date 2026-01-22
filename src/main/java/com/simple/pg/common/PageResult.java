package com.simple.pg.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private long total;

    private List<T> itemList;

    private boolean success;

    private boolean hasNext;
}
