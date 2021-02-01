package org.example.model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString

//返回给前端的 结果对象
//文档合并的结果返回给前端
public class Result {

   //合并文档排序用
    private Integer id;//docInfo 的id 文档合并时，文档的身份标识
    private int Weight ; //同一个文档合并后，权重相加再排序//像哟啊初始化的时候就是0 所以写成 int

    //title url desc这三个都是前端写好的，根据前端的写好的
    //然后规定给后端必须返回的
    private String title; //对应文档的标题  -- DocInfo的标题
    private String url; // DocInfo的url
    private String desc; // docInfo 的content   超长时，截取指定长度

}
