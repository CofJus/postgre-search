package com.simple.pg.utils;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ChineseSegmentUtil {

    //
    private final JiebaSegmenter segmenter = new JiebaSegmenter();

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
    public List<String> segment(String text) {
        // 空值处理
        if (text == null || text.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // 结巴分词（INDEX模式适合检索）
        List<SegToken> segTokens = segmenter.process(text.trim(), JiebaSegmenter.SegMode.INDEX);
        List<String> keywords = new ArrayList<>();

        for (SegToken token : segTokens) {
            String word = token.word.trim().toLowerCase();
            // 过滤条件：非停用词 + 长度>1（过滤单字） + 非空
            if (!STOP_WORDS.contains(word) && word.length() > 1 && !word.isEmpty()) {
                keywords.add(word);
            }
        }

        return keywords;
    }

    /**
     * 将关键词列表转为PostgreSQL的tsvector格式字符串
     * 示例：["postgresql", "全文索引"] → "'postgresql':1 '全文索引':2"
     */
    public String toTsVectorString(List<String> keywords) {
        if (keywords.isEmpty()) {
            return "''"; // 空tsvector
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
     * 将检索关键词转为PostgreSQL的tsquery格式字符串（AND逻辑）
     * 示例："PostgreSQL 全文索引" → "'postgresql' & '全文索引'"
     */
    public String toTsQueryString(String searchText) {
        List<String> keywords = segment(searchText);
        if (keywords.isEmpty()) {
            return "''"; // 空tsquery
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keywords.size(); i++) {
            String word = escapeSpecialChars(keywords.get(i));
            sb.append("'").append(word).append("'");
            if (i < keywords.size() - 1) {
                sb.append(" & ");
            }
        }
        return sb.toString();
    }

    /**
     * 转义PostgreSQL tsvector/tsquery中的特殊字符
     * 需转义：单引号(')、反斜杠(\)、&、|、!、:、*、(、)
     */
    private String escapeSpecialChars(String word) {
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
}