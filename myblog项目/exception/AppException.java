package org.example.exception;

/**
 * 自定义异常类：业务代码抛自定义异常类或者其他异常
 * 自定义异常返回给定的错误码，其他异常返回错误码
 */
public class AppException  extends RuntimeException{

    //给前端返回的json 字符串中，
    private String code;


    public AppException(String code,String message) {

//        super(message);//要构造子列必须要先帮助父类构造
//        this.code = code;
        /**
         * 与下面的等效 代码冗余，理解为什么要这么写
         */
        this(code,message,null);

    }

    public AppException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
