package org.example.servlet;

import org.example.dao.ArticleListDAO;
import org.example.model.Article;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet{

    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //为什么要获取session -- 目的是为了从session里面取用户 -- 为什么要从session 里面获取用户？
        //根本原因是考虑到数据库的约束，从表主表之间的id 关系
        //不能随便插入一个id 不存在在数据库id
        //所以需要从session 里面获取到id 使用
        HttpSession session = req.getSession(false);//传入的参数?
         User user = (User)session.getAttribute("user");

        //请求数据类是ApplicationJson 是需要输入流获取
        InputStream is = req.getInputStream();
        //做反序列化，把json 对象 反序列化为java 对象
        Article a = JSONUtil.deserialize(is,Article.class); // 反序列为文章类 - 为了获取title和 content
        a.setUserId(user.getId());//你注意这里的id 的设置
        //因为主表从表id 外键的原因，id 是由约束的
        int  num = ArticleListDAO.insert(a);
        return null;
    }
}
