package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * http响应json数据格式，前后端统一约定的json数据格式
 * 响应状态码都是200 ，进入ajax的success来使用
 * {success:ture , data:XXX}
 * {success :false ,code: XXX message: XXX}
 */
@Getter /*自动生成方法，可以不用手动配置*/
@Setter
@ToString
public class JSONResponse {

    //业务操作是否成功
    private boolean success;
    //业务操作的消息码，一般来说是出现错误才有意义，给开发人员定位问题的
    private String code;
    //业务操作出错的，给用户看的信息
    private String message;
    //业务数据，业务操作成功是时，给前端ajax success 使用，解析响应的json数据格式，渲染网页
    private Object data;


}
