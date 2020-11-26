package org.example.servlet;
//Servlet 处理Http 的请求和响应
//它是属于javaEE 里面定义的东西  所以需要引入一个依赖包
//javaSE 里面的东西不需要引入依赖包
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//注解的使用，小括号包裹多个属性，属性名= 属性值，多个之间逗号间隔
//@WebServlet(value = {""，“”}, name  = "" )
//属性名为value 时可以缺省
//Servlet 定义服务：注意服务路径必须是/ 开头，否则 tomcat 的启动会报错
@WebServlet("/login")  // 书写注解
//@WebServlet(value = "")//value 这个属性默认可以不写
public class LoginServlet extends HttpServlet {

    //重写dopost方法
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求，相应编码，及响应数据类型
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        //解析数据
        String u = req.getParameter("username");//类似于map 的键值对的形式，通过键来获取值
        String p = req.getParameter("password");
        System.out.printf("=====用户名(%s) 密码(%s) =====%n ",u,p); //无脑的%s 就好啦

        //返回响应数据
        PrintWriter pw = resp.getWriter();//response 获取io 的输出流
        pw.println("登陆成功");
        pw.println("欢迎你 ，"+ u); // 以换行的额方式打印一些内容 -- <h3> 可以利用前端的格式
        pw.flush();//有缓冲的io 操作，需要刷新缓冲区，才会真正的刷新数据
        pw.close();//io 流操作记得关闭资源


    }
}
