package com.qiruxie.tomcat;

import com.qiruxie.standard.Servlet;

/**
 * 默认的web应用是/
 */
public class DefaultContext extends Context {
    public DefaultContext(ConfigReader reader) {
        //reader 是传进来的 name 是/固定的
        super("/",reader);
    }


    //无论你那这那一个Servlet的路径来找我 我返回的都是 不存在的Servlet
    @Override
    public Servlet get(String servletPath) {
        return HttpServer.notFoundServlet;
    }
}
