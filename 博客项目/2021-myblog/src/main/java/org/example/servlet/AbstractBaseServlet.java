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

public abstract class AbstractBaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求体编码格式
        req.setCharacterEncoding("UTF-8");
        //设置响应体的编码格式
        resp.setCharacterEncoding("UTF-8");
        //设置响应体的数据类型（浏览器要采用什么方式执行）
        /* resp.setContentType("text/html")*/;
        resp.setContentType("application/json");

        //session会话管理，除录和注册接口其他都需要登陆后访问
        //req.getServletPath()获取请求的服务路径
        //TODO
        JSONResponse json = new JSONResponse();
        try{
            //调用子类重写的方法
            Object data =  process(req, resp);
            json.setSuccess(true);
            json.setData(data);
        }catch (Exception e){
            e.printStackTrace();//不要吃异常，就是捕获到了啥也不干

            //异常如何处理 JDBC JSON
            //自定义异常返回错误消息
            String s = "未知的错误";
            String code ="Unknown";
            if( e instanceof AppException){
                //为什么强制类型转换
                code = ((AppException) e).getCode();
                s = e.getMessage();//这里不用强制转换是因为e本事既有getmessage
                //json.setSuccess(false);   //不用设置了因为基本数据类型new出来的时候就已经时false
            }
            json.setCode(code);   //要取出自定义异常的错误码然后设置到json返回数据里面去
            json.setMessage(s);
        }

        //不管是执行try还是catch里面的都需要把内容返回给前端
        PrintWriter pw =  resp.getWriter();
        pw.println(JSONUtil.serialize(json)); //打印的时候就把java对象序列化json字符串
        //这里的java对象是 json这个对象
        pw.flush();
        pw.close();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;


    //和 JSON Respnse data 的数据类型一样
   // process 返回的数据类型是根据具体业务来定的
    //有的业务返回的是文章列表
    //有的返回的是null

}
