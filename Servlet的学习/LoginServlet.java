package org.example.servlet;

import org.example.dao.LoginDAO;
import org.example.exception.AppException;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login") // 指定服务路径
public class LoginServlet extends  AbstractBaseServlet {
    //为什么可以跑去执行父类的代码
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {


        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //数据库表到实体类之间的映射？？
        User user = LoginDAO.query(username);

        //为什么要把它封装成一个user 不直接用数据库返回的结果比较密码

        if(user == null) {
            throw new AppException("Login002","用户不存在");
        }
        if(!user.getPassword().equals(password)) {
            throw new AppException("Login003","用户名或密码错误");
        }

        //登录的时候要管理会话 创建session
        HttpSession session = req.getSession();
        session.setAttribute("user",user);


        return  null;

//        if() {
//            //模拟用户名密码校验
//            return null;
//        }else {
//            //解析不通过的时候，是在父类里面处理的异常
//            throw new AppException("Login001","用户名的密码错误"); //这个是怎么用的
//        }

    }
}
