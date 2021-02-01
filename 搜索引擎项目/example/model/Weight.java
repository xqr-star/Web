package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 倒排索引中Map<String,List<Weight>   > 关键词对应的信息
 */
@Getter
@Setter
@ToString
public class Weight {
    private DocInfo doc;
    private int weight = 0 ;//权重值 通过标题和正文计算
    private String keyWord; // 这个关键词 和map 的String 一样，只是保留后续可能会用

}
