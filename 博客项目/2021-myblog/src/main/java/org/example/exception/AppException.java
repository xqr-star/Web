package org.example.exception;

//处理异常就是客户端要能够清楚的看懂是什么异常
//运行时异常---编译时异常

import lombok.Getter;

@Getter
//这里setter 是通过new对象设置构造方法的时候，加入进入的
//自定义异常返回给定的错误码，其他异常统一返回其他错误码
public class AppException extends RuntimeException {

    //给前端返回的json字符串中，板寸错误码
    //那么code从那里面来呢
    private String code;

    //继承类，重写方法
    public AppException(String code,String message) {
      /*  super(message);
        this.code = code;*/

        //后面这种写法解决代码冗余问题
        this(code,message,null);
    }

    public AppException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
