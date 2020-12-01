package org.example.servlet;

import org.example.exception.AppException;
import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

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

        req.setCharacterEncoding("UTF-8");//设置请求体编码格式
        resp.setCharacterEncoding("UTF-8");//设置响应体编码格式

        ///设置响应体的数据类型（浏览器要采取什么方式运行）
        resp.setContentType("application/json");
        //resp.setContentType("text/html");

        //Session 会话管理：除了登录和注册接口，其他都需要登陆后访问
        //TODO
        //req.getServletPath();//获取请求服务路径

        JSONResponse json = new JSONResponse();
        //我这个json try 里面要用 catch 也要用
        try{
            //调用子类重写的方法
            Object data = process(req,resp);
            //子类的process 方法执行完没有抛出异常就表示业务执行成功。

            //如果捕捉道异常后面的代码不执行
            json.setSuccess(true);
            json.setData(data);
        }catch (Exception e){
            //捕获自己写代码可能出现的异常
            //JDBC 的异常，JSON 处理的异常，
            //自定义异常返回错误消息
            e.printStackTrace(); // 打印异常-- 不要吃异常

            //json.setSuccess(false); 不用写的原因就是new默认的属性值时false
            String code = "Unknown";
            String s = "未知的错误";

            //捕获的异常对象是某一个接口的类
            if(e instanceof AppException) {
                code = ((AppException) e).getCode();//为什么类型强制转换？
                s = e.getMessage();
            }

            json.setCode(code);//成功默认值是null
            json.setMessage(s);

        }

        //需要把json 对象序列化成字符串

        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize(json));
        pw.flush();
        pw.close();
    }


    // 子类可以用的  子类重写这个方法
    protected abstract Object process(HttpServletRequest req,
                            HttpServletResponse resp) throws Exception; // 抽象类里面的抽象方法不能有具体实现




}
