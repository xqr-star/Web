package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;
import org.example.model.User;
import org.example.util.JSONUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //为什么要获取session -- 目的是为了从session里面取用户 -- 为什么要从session 里面获取用户？
        //根本原因是考虑到数据库的约束，从表主表之间的id 关系
        //不能随便插入一个id 不存在在数据库id
        //所以需要从session 里面获取到id 使用
        HttpSession session = req.getSession(false);//传入的参数不创建
        User user = (User)session.getAttribute("user");


        //由于请求的数据是application /json所以要使用输入流获取
        //如果请求的类型是 application/formdata 就可以使用Parameters的方法
        ServletInputStream is = req.getInputStream();

        //将json字符串反序列化为java的 Article的对象
        Article article = JSONUtil.deserialize(is,Article.class);
        //注意这里的id 的设置
        article.setUserId(user.getId());
        //因为主表从表id 外键的原因，id 是由约束的
        int num = ArticleDao.insert(article);

        return null;
    }
}
