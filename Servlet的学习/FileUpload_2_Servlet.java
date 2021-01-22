package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/test4")
@MultipartConfig//接收form-data格式的请求
public class FileUpload_2_Servlet extends HttpServlet {

    /**
     * 接收multipart/form-data请求数据类型
     * 多个键值对的方式，任意键值对都可以是数据，也可以是文件
     * 所以form-data支持多文件上传+多数据传输
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        Part p1 = req.getPart("p1");//通过键获取值p1=测试一下p1
        Part p2 = req.getPart("p2");//通过键获取值p2=测试一下p2
        Part p3 = req.getPart("p3");//通过键获取值p3=选择本地文件
        Part p4 = req.getPart("p4");//通过键获取值p4=选择本地文件
        //可以1.打断点 2.查看api文档 3.idea通过对象打点查看方法
        System.out.println(p1.getName());//获取键
        System.out.println(p2.getName());
//        p1,p2值可以通过输入流获取
        //p3,p4是上传的文件，可以通过输入流获取数据，也可以write到本地硬盘
        p3.write("E://1.png");
        p4.write("E://2.png");

        //因为是post提交，所以返回json（返回网页没意义，网页都是get请求显示）
        resp.setContentType("application/json");
        resp.getWriter().println("OK");

//        pw.flush();
    }
}