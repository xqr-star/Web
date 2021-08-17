package com.qiruxie.standard;

//自定义异常 该异常时受查异常
public class ServletException extends Exception {
    public ServletException(){
        super();
    }

    public ServletException(String message){
        super(message);
    }

    public ServletException(String message,Throwable cause){
        super(message,cause);
    }

    public ServletException(Throwable cause){
        super(cause);
    }
}
