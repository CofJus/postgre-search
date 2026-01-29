package com.simple.pg.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChineseSegmentUtil {

    private static final JiebaSegmenter segmenter = new JiebaSegmenter();

    // 中文停用词表（可从配置文件加载，这里简化硬编码）
    private static final Set<String> STOP_WORDS = new HashSet<>();

    static {
        // 初始化常见停用词
        STOP_WORDS.add("的");
        STOP_WORDS.add("了");
        STOP_WORDS.add("在");
        STOP_WORDS.add("是");
        STOP_WORDS.add("我");
        STOP_WORDS.add("你");
        STOP_WORDS.add("他");
        STOP_WORDS.add("它");
        STOP_WORDS.add("都");
        STOP_WORDS.add("也");
        STOP_WORDS.add("和");
        STOP_WORDS.add("就");
        STOP_WORDS.add("而");
        STOP_WORDS.add("及");
        STOP_WORDS.add("与");
        STOP_WORDS.add("着");
        STOP_WORDS.add("之");
        STOP_WORDS.add("于");
        STOP_WORDS.add("这");
        STOP_WORDS.add("那");
        STOP_WORDS.add("此");
        STOP_WORDS.add("等");
        STOP_WORDS.add("个");
        STOP_WORDS.add("只");
        STOP_WORDS.add("还");
        STOP_WORDS.add("被");
        STOP_WORDS.add("把");
        STOP_WORDS.add("对");
        STOP_WORDS.add("或");
        STOP_WORDS.add("能");
        STOP_WORDS.add("可");
    }

    /**
     * 中文分词核心方法（精准模式）
     * @param text 待分词文本
     * @return 过滤停用词后的关键词列表
     */
    public static List<String> segment(String text) {
        // 空值处理
        if (text == null || text.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<SegToken> segTokens = segmenter.process(text.trim(), JiebaSegmenter.SegMode.INDEX);
        List<String> keywords = new ArrayList<>();

        for (SegToken token : segTokens) {
            String word = token.word.trim().toLowerCase();
            // 过滤条件：非停用词 + 长度>1（过滤单字） + 非空
            if (!STOP_WORDS.contains(word) && word.length() > 1) {
                keywords.add(word);
            }
        }

        return keywords;
    }

    /**
     * 文本向量化
     */
    public static String toTsVectorString(List<String> keywords) {
        if (keywords.isEmpty()) {
            return "''";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            String word = escapeSpecialChars(keywords.get(i));
            sb.append("'").append(word).append("':").append(i + 1);
            if (i < keywords.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 精准搜索
     */
    public static String toAccurateTsQueryString(String searchText) {
        if (null == searchText || searchText.trim().isEmpty()) {
            return "";
        }
        if (searchText.length() == 1) {
            return "'" + searchText + ":*'";
        }

        List<String> keywords = segment(searchText);
        if (CollectionUtils.isEmpty(keywords)) {
            return "''";
        }
        List<String> filteredKeywords = keywords.stream()
                .map(ChineseSegmentUtil::escapeSpecialChars).toList();
        return String.join(" & ", filteredKeywords);
    }

    /**
     * 特殊字符转义
     */
    private static String escapeSpecialChars(String word) {
        if (word == null) {
            return "";
        }
        return word.replace("\\", "\\\\")
                .replace("'", "''")
                .replace("&", "\\&")
                .replace("|", "\\|")
                .replace("!", "\\!")
                .replace(":", "\\:")
                .replace("*", "\\*")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }

    public static void main(String... args) {
        String text = "PostgreSQL全文索引的实现与应用";
        List<String> keywords = segment(text);
        System.out.println("Segmented Keywords: " + keywords);
        System.out.println(toTsVectorString(keywords));
    }
}