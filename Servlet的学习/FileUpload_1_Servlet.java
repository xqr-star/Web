package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/test3")
public class FileUpload_1_Servlet extends HttpServlet {

    /**
     * 文件上传：使用二进制流数据格式（只能上传一个文件）
     * 请求Content-Type：application/octet-stream
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //获取请求的文件
        InputStream is = req.getInputStream();
        //拿到输入流，想打印还是保存在服务器本地，就是自己业务来定
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] input = new byte[1024*24];
        int len;//读取io流的长度
        while((len=bis.read(input)) != -1){
            System.out.println(new String(input, 0, len));
        }

        //因为是post提交，所以返回json（返回网页没意义，网页都是get请求显示）
        resp.setContentType("application/json");
        resp.getWriter().println("OK");

//        pw.flush();
    }
}