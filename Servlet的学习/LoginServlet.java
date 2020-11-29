package org.example.servlet;

import org.example.exception.AppException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login") // 指定服务路径
public class LoginServlet extends  AbstractBaseServlet {
    @Override
    protected void process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if("abc".equals(username)) {
            //跳转到文章的页面-- 使用重定向、
            resp.sendRedirect("jsp/articleList.jsp");
        }else {
            throw new AppException("用户名的密码错误");
        }
    }
}
