package com.qiruxie.standard.http;

//注意这里的一些方法
public interface HttpSession {

    Object getAttribute(String name);

    void removeAttribute(String name);

    void setAttribute(String name,Object value);

}
