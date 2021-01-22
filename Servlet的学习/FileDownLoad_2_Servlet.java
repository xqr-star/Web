package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/test2")
public class FileDownLoad_2_Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        //返回文件（浏览器下载文件）
        resp.setContentType("application/octet-stream");
        //读取本地硬盘文件，再输出
        FileInputStream fis = new FileInputStream("E:\\Doc\\jdk+api+1.8.CHM");
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream os = resp.getOutputStream();
        byte[] input = new byte[1024*24];
        int len;//读取io流的长度
        while((len=bis.read(input)) != -1){
            os.write(input, 0, len);
        }

//        pw.flush();
    }
}
