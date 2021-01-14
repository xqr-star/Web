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
@WebServlet("/sen")
public class SensitiveServlet extends HttpServlet {

   //如果客户端发出的是get请求，会出现405--找不到方法
   // 但是-url映射到了
    @Override
    /**
     * 每次http请求映射到某个Servlet资源的时候，都会调用service的声明周期方法
     * 如果没有请求方法没有重写，就用父类的doxXXX(请求方法)
     * 如果重写，就将会将请求数据包装为一个Request请求对象，
     * 这时候虽然还没有响应，但是也包装了一个Response响应对象
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("utf-8");
       resp.setCharacterEncoding("utf-8");
       //设置相应数据列下
       resp.setContentType("text/html");
        //返回响应内容数据

        PrintWriter pw = resp.getWriter(); // response 获取io的输出流
        HttpSession session = req.getSession(false);//本质是获取到req请求的sessionid
         //对应到服务端找session对象，没有找到就会返回null
        if(session == null){
            //有两种情况 一种是没有cookie 就把cookie删除了以后就没有了
            //还有一种时重启了服务器，之气那保存咋tomcat内存里面的seession对象就没有了
            //cookie字符串映射不到对应的session对象也是空
            System.out.println("session为空");
            resp.setStatus(401);//没有登录，不允许访问 返回401，表示Unauthorized
            pw.println("敏感资源登陆后访问");
        }else {
            //有可能出现session不为空，但是uesrname为空
            String username =(String) session.getAttribute("username");
            System.out.println("session存在，用户存在"+username);
            pw.println("登陆成功 "); // 以换行的方式响应内容


            /*//伪代码：禁止访问的敏感资源（没有权限）
            User user = (User) session.getAttribute("username");
            if(user.允许访问的资源不包含当前敏感资源uri){
                resp.setStatus(403);//表示登录了，但是不允许访问  forbidden
            }*/
        }


        pw.flush();//有缓冲的io需要刷新
        pw.close();//io关闭资源




    }
}
