package org.example.model;

/**
 * http响应json 数据，前后端同一的json 格式
 * 响应的状态码都是200 键入ajax 的success来使用
 * 操作成功前端解析的内容：success :true data:XXx
 * 操作失败 false code message
 */

//这一步时通过下载那个工具包 然后自动会生成getter和setter方法
//不会出现我加进去一个或者删除一个就要把所有的getter 是setter删除重写
//加上这三个注解之后，既不用getter和setter方法
//@Getter
//@Setter
//@ToString
public class JSONResponse {
    //业务操作是都成功
    private  boolean success;
    //业务操作的消息码：一般来说，出现错误时的错误码才有意义（开发员看的），定位错误问题
    private String code;
    //业务操作的错误消息 给用户看的信息
    private  String message;
    //业务操作成功的时候，给前端的ajax success 方法使用，响应ajax 数据
    private  Object data;

    //为什么会想到写getter/setter？ private 表示只能在该类里面进行使用


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
