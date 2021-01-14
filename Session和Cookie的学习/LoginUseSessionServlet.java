package org.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//注解的使用：小括号包裹多个属性，属性名=属性值，多个之间逗号间隔
//属性名为value的时候可以省略
//servlet 定义服务路径/开头
@WebServlet("/loginUseSession")
public class LoginUseSessionServlet extends HttpServlet {

   //如果客户端发出的是get请求，会出现405--找不到方法
   // 但是-url映射到了
    @Override
    /**
     * 每次http请求映射到某个Servlet资源的时候，都会调用service的声明周期方法
     * 如果没有请求方法没有重写，就用父类的doxXXX(请求方法)
     * 如果重写，就将会将请求数据包装为一个Request请求对象，
     * 这时候虽然还没有响应，但是也包装了一个Response响应对象
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");
       resp.setCharacterEncoding("utf-8");
       //设置相应数据列下
       resp.setContentType("text/html");

       //解析数据
        String u = req.getParameter("username");
        String p = req.getParameter("password");

        System.out.printf("用户名 %s  密码%s %n",u,p);





        //返回响应内容数据

        PrintWriter pw = resp.getWriter(); // response 获取io的输出流
        if("abc".equals(u) && "123" .equals(p)) { //模拟数据库校验
            HttpSession session = req.getSession();  // 这个session对象是Tomcat封装好的，所以我们可以直接用--就好上面的的req 和resp对象一样，我们为什么可以直接用的原因都是tomcat 做到的面向对象三大特性之一的封装
            //默认传递参数是true
            //上面表示的是获取Session信息（从客户端获取jsession id ，在服务端Map中找到session对象），参数为false表示的是如果获取不到，返回空
            //参数为true ，如果获取不到，就创建一个(服务端创建)，再返回
            session.setAttribute("username",u);
            session.setAttribute("password",p);
            //session.setMaxInactiveInterval("user",user);//真实操作时查询用户信心，转换为user 对象，只是现在没有创建这个对象而已
            pw.println("登陆成功 "); // 以换行的方式响应内容
            pw.println("欢迎你"+u); // 以换行的方式响应内容
        }else {
            pw.println("用户名密码错误，登录失败！");
        }

        pw.flush();//有缓冲的io需要刷新
        pw.close();//io关闭资源




    }
}
