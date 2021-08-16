package com.qiruxie.tomcat.http;

import com.qiruxie.standard.http.Cookie;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * 请求类
 * 是在解析所有的输入流之后得到类
 * 这个类是在解析完所有可能用到的请求关键字之后得到的一个类
 * 同时因为解析到了一些关键的数据 模拟的tomcat 会根据cookie 里面是否有sessionid来去创建session对象
 */
public class Request implements HttpServletRequest {

    private final String method;
    private final String requestURI;
    private final String contextPath;
    private final String servletPath;
    private final Map<String,String> parameters;
    private final Map<String,String> headers;
    private final List<Cookie> cookieList;

    private  HttpSession session = null;



    public Request(String method, String requestURI, String contextPath, String servletPath, Map<String, String> parameters, Map<String, String> headers, List<Cookie> cookieList) throws IOException, ClassNotFoundException {
        this.method = method;
        this.requestURI = requestURI;
        this.contextPath = contextPath;
        this.servletPath = servletPath;
        this.parameters = parameters;
        this.headers = headers;
        this.cookieList = cookieList;

        for(Cookie cookie : cookieList){
            if(cookie.getName().equals("session-id")){
                String sessionId = cookie.getValue();

                //这里说明找找到了sessionid 那么就可以拿着这个seesion id
                session = new HttpSessionImpl(sessionId);
                break;
            }
        }

    }


    @Override
    public String toString() {
        return String.format("Request{%s %s %s %s}", method, requestURI, parameters, session);
    }

    @Override
    public Cookie[] getCookies() {
        //本身得到就是一个object数组 要想得到cookie数组只能这么做
        return cookieList.toArray(new Cookie[0]);
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getContentPath() {
        return contextPath;
    }

    @Override
    public String getServletPath() {
        return servletPath;
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }

    @Override
    public HttpSession getSession() {
        //遍历所有的cookie 找到是否包含session-id的
        //如果没有 没有session
        //如果由 那么就可以拿到session-id
        //然后可以根据session-id转换成对应的文件名
        //然后检查这个文件是否存在 - 过期 伪造
        //然后使用反序列化读取文件内存储的对象

        //这里是怎么做的 我不知道是不是应该传递参数


        //只有在getSession的时候才会创建嗷！
        if(session == null){
            //如果session 是空的 那么说明就没有sessionId 返回为无参的
            //cookie里面的没有session id 字段那么就由服务器创建一个随机的sessionid返回
            return new HttpSessionImpl();
        }
        return session;
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }
}
