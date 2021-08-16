package com.qiruxie.standard.http;

import com.qiruxie.standard.ServletRequest;

//这些都是参考官方写的
public interface HttpServletRequest extends ServletRequest {

    Cookie[] getCookies();


    //获取请求头 根据key 获取value
    String getHeader(String name);

    String getMethod();

    String getContentPath();

    String getServletPath();

    String getRequestURI();


    //为什么是请求getSession
    // getSession 有两个版本 一种是创建的一种是不创建的，这里保留一个就可以啦
    HttpSession getSession();


}
