package com.qiruxie.standard.http;

import com.qiruxie.standard.Servlet;
import com.qiruxie.standard.ServletException;
import com.qiruxie.standard.ServletRequest;
import com.qiruxie.standard.ServletResponse;

import java.io.IOException;

public abstract class HttpServlet implements Servlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {

        //判断当前的协议 是否符合http协议 也就是请求是不是http请求，响应是不是http响应
        if(req instanceof HttpServletRequest && resp instanceof HttpServletResponse){
            HttpServletRequest httpReq = (HttpServletRequest) req;

            HttpServletResponse httpResp = (HttpServletResponse) resp;

            service(httpReq,httpResp);//重载方法

        }else {
            throw new ServletException("不支持非Http协议");
        }
    }



    public void service(HttpServletRequest httpServletRequest ,HttpServletResponse httpServletResponse) throws IOException {
        if(httpServletRequest.getMethod().equals("GET")){ //方法是大写的
            doGet(httpServletRequest,httpServletResponse);
        }else {
            httpServletResponse.sendError(405);//方法不支持
        }
    }

    public  void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendError(405);//这里就是说直接返回405
        //如果没有重写doGet方法就返回
    }

    @Override
    public void destroy() {

    }
}
