package org.example.servlet;

import org.example.exception.AppException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login") // 指定服务路径
public class LoginServlet extends  AbstractBaseServlet {
    //为什么可以跑去执行父类的代码
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {


        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if("abc".equals(username)) {
            //模拟用户名密码校验
            return null;
        }else {
            //解析不通过的时候，是在父类里面处理的异常
            throw new AppException("Login001","用户名的密码错误"); //这个是怎么用的
        }
    }
}
