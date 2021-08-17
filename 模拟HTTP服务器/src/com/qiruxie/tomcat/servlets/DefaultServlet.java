package com.qiruxie.tomcat.servlets;

import com.qiruxie.standard.ServletException;
import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;
import com.qiruxie.tomcat.HttpServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DefaultServlet extends HttpServlet {

    //设置一个/ 对应访问的静态资源
    private final String welcomeFile = "/index.html";

    //保存一个映射关系 根据静态资源的后缀和响应type进行映射
    private final Map<String,String> mime = new HashMap<>();

    private final String defaultContentType = "text/plain";


    @Override
    public void init() throws ServletException {
        mime.put("htm","text/html");
        mime.put("html","text/html");
        mime.put("jpg","image,jpeg");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("由我处理静态资源啦！");

        String contextPath = req.getContentPath();
        String servletPath = req.getServletPath();

        if(servletPath.equals("/")){
            servletPath = welcomeFile;
        }

        String fileName = String.format("%s\\%s\\%s", HttpServer.WEB_BASE,contextPath,servletPath);
        File file = new File(fileName);

        if(!file.exists()){
            //如果静态资源不存在 返回404
            //req.dispatch.forward()
            HttpServer.notFoundServlet.service(req,resp);
            return;
        }
        //如果静态资源存在 就去解析静态资源
        //设置响应的格式
        //解析静态资源的内容 然后设置到响应体里面去


        //获取映射关系设置响应体
        String contentType = getContentType(servletPath);
        resp.setContentType(contentType);

        //读取文件中的内容写回响应体

        OutputStream outputStream = resp.getOutputStream();

        //读取文件作为输入
        try(InputStream inputStream = new FileInputStream(fileName)){
           byte[] buffer =new byte[1024];
           int length = -1;
           while ((length  = inputStream.read(buffer) ) != -1){
               outputStream.write(buffer,0,length);
           }
            outputStream.flush();
        }


    }

    private String getContentType(String servletPath) {
        int index = servletPath.lastIndexOf('.');
        String contentType = defaultContentType;
        if(index !=-1){
            String extension = servletPath.substring(index + 1);
            contentType = mime.getOrDefault(extension,defaultContentType);
        }
        return contentType;

    }
}
