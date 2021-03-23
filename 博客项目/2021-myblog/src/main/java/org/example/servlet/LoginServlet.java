package org.example.servlet;

import org.example.dao.LoginDao;
import org.example.exception.AppException;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//请求来了之后，最先调用的是顶级的sevice 方法
//由于父类继承了它所以调用父类的get/post方法，然后调用子类重写process方法
//登录功能就是用户登录之后，为用户创建了一个session保存在服务器，但是为什么是用req
@WebServlet("/login")
public class LoginServlet  extends AbstractBaseServlet{



    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //解析请求数据--从请求体中获取数据
        String username = req.getParameter("username");
        String password = req.getParameter("password");


        User user = LoginDao.query(username);//表示根据用户名去查询最后返回的是这个用户

        if(user == null){
            throw new AppException("LG002","用户不存在");
        }
        if(!user.getPassword().equals(password)){
            throw new AppException("LG003","用户名或密码错误"); // 明明比对的是密码，包这个错误有可能写道别人的账号了
        }

        //登录成功，创建session
        HttpSession session = req.getSession();
        session.setAttribute("user",user);

        return  null;





//        if("abc".equals(username)){ //模拟用户登录成功
//            return null;
//        }else {
//            throw  new AppException("LG001","用户名密码错误");
//        }
    }
}
