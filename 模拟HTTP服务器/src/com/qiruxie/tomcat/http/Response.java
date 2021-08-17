package com.qiruxie.tomcat.http;

import com.qiruxie.standard.http.Cookie;
import com.qiruxie.standard.http.HttpServletResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response implements HttpServletResponse {

    public final List<Cookie> cookieList ;
    public final Map<String,String> headers ;
    public int status = 200;
    public final  ByteArrayOutputStream bodyOutputStream;
    public final PrintWriter bodyPrintWriter;


    @Override
    public String toString() {
        try {
            bodyPrintWriter.flush();
            bodyOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
        return String.format("Response{%d %s %s }",status,headers,bodyOutputStream.toString());
    }

    //由构造方法对所有的属性进行设置
    public Response() throws UnsupportedEncodingException {
        cookieList = new ArrayList<>();
        headers = new HashMap<>();
        bodyOutputStream = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(bodyOutputStream,"UTF-8");
        bodyPrintWriter = new PrintWriter(writer);
    }

    @Override
    public void addCookie(Cookie cookie) {
        cookieList.add(cookie);
    }

    @Override
    public void sendError(int sc) {
        //TODO
    }

    @Override
    public void sendRedirect(String location) {
        setStatus(307);
        headers.put("Location",location);
    }

    @Override
    public void setHeader(String name, String value) {
        headers.put(name,value);
    }

    @Override
    public void setStatus(int sc) {
        status = sc;
    }


    //这个是写入响应体的东西 byte
    @Override
    public OutputStream getOutputStream() throws IOException {
        return bodyOutputStream;
    }


    //写入响应体 text /char
    @Override
    public PrintWriter getWriter() throws IOException {
        return bodyPrintWriter;
    }

    @Override
    public void setContentType(String type) {
        if(type.startsWith("text/")){
            type = type+";charset=utf-8";
        }
        setHeader("Content-Type",type);
    }
}
