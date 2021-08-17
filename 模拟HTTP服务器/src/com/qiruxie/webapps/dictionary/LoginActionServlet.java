package com.qiruxie.webapps.dictionary;

import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;
import com.qiruxie.standard.http.HttpSession;

import java.io.IOException;

/**
 * 这里的操作暂时是不支持数据库的，所以使用本地方式就好啦
 */
public class LoginActionServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(username .equals("xqr") && password.equals("123")){
            User user = new User(username,password);
            //第一次登录的时候 创建一个session
            //看从请求解析的里面有没有  session 没有就创建
            //然后加入session 对象
            HttpSession session = req.getSession();
            session.setAttribute("user",user);

            //这里的路径是不可以带/的
            resp.sendRedirect("profile-action");

        }else {
            resp.sendRedirect("login.html");
        }
    }
}
