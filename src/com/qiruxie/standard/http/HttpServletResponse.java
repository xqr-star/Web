package com.qiruxie.standard.http;

import com.qiruxie.standard.ServletResponse;

public interface HttpServletResponse extends ServletResponse {


 //接口继承了接口继承了接口里面的而所有方法，同时也可以解自己的方法

   //这里就是http的set-cookie属性的
    void addCookie(Cookie cookie);


    void sendError(int sc);

    //重定向
    void sendRedirect(String location);


    //设置头信息
    void setHeader(String name,String value);

    //设置状态码
    void setStatus(int sc);


}
