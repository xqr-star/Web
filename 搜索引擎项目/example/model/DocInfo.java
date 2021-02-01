package org.example.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 每一个本地的html文件对应一个文档对象
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DocInfo {
    private Integer id;//类似数据库主键
    private String title;//标题 使用html的文件名
    private String url; // 这个url是oracle官网的前缀+本地的路径拼接好的
    private String content;//网页正文：<标签>内容</标签> 取内容为正文
}
