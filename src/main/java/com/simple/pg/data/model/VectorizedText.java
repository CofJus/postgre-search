package com.simple.pg.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Rui
 * @date 2026/1/29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VectorizedText {

    private String keywords;

    private String tsv;

    public static VectorizedText of(List<String> keywords, String tsv) {
        return new VectorizedText(String.join(" ", keywords), tsv);
    }
}
