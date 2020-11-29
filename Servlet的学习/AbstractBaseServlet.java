package org.example.servlet;

import org.example.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractBaseServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);//调用都post的方法

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");//设置响应头

            //Session 会话管理：除了登录和注册接口，其他都需要登陆后访问
            //TODO
            //req.getServletPath();//获取请求服务路径

            //调用子类重写的方法
            process(req, resp);
        }catch (Exception e){
            //捕获自己写代码可能出现的异常
            //JDBC 的异常，JSON 处理的异常，
            //自定义异常返回错误消息
            e.printStackTrace(); // 打印异常-- 不要吃异常
            String s = "未知的错误";
            //捕获的异常对象是某一个接口的类
            if(e instanceof AppException) {
                s = e.getMessage();
            }
            PrintWriter pw = resp.getWriter();
            pw.print(s);
            pw.flush();
            pw.close();

        }
    }


    // 子类可以用的  子类重写这个方法
    protected abstract void process(HttpServletRequest req,
                            HttpServletResponse resp) throws Exception; // 抽象类里面的抽象方法不能有具体实现




}
