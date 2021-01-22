package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/Test2")
/*
文件下载
 */
public class Test2 extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //返回文件--浏览器响应设置这个格式--就是文件下载
        //如果请求设置这个格式--就是文件上传
        //响应设置这个就是下载
        resp.setContentType("application/octet-stream");

        //先读取本地文件，然后io流输出到返回响应的数据
        //先复制的网卡上，然后基于servlet 定义的content-type 决定了
        // 会下载文件到浏览器的默认文件夹


        String str = new String("E:\\javacode\\File-IO\\Test1.txt");

        FileInputStream fis = new FileInputStream(str);

        //使用缓冲流读取
        BufferedInputStream bis = new BufferedInputStream(fis);
        byte[] bytes = new byte[1024];

        //定义输出流--二进制流
        OutputStream os = resp.getOutputStream();


        int length = 0;//返回读取的长度
        while ((length = bis.read(bytes)) != -1){
            os.write(bytes,0,length);
        }


    }
}
