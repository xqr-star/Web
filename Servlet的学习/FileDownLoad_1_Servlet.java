package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test")
public class FileDownLoad_1_Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        //返回网页
//        resp.setContentType("text/html");
        //返回文件（浏览器下载文件）
        resp.setContentType("application/octet-stream");
        PrintWriter pw = resp.getWriter();
        pw.println("<!DOCTYPE html>");
        pw.println("<html lang=\"en\">");
        pw.println("<head>");
        pw.println("<meta charset=\"UTF-8\">");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h2>用户登录</h2>");
        pw.println("<input type=\"text\" placeholder=\"吃了吗\">");
        pw.println("</body>");
        pw.println("</html>");
//        pw.flush();
    }
}
