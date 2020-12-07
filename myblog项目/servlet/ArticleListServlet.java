package org.example.servlet;

import org.example.dao.ArticleListDAO;
import org.example.exception.AppException;
import org.example.model.Article;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
@WebServlet("/articleList")
public class ArticleListServlet  extends  AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //获取session  是从cookie 里面获取的
        HttpSession session = req.getSession(false);
        boolean notLogin = true;
        if(session == null)
            throw new AppException("ART002","用户没有登录不允许访问");

        //获取登陆时创建的session 保存的用户信息
        User user = (User)session.getAttribute("user");
        //为什么会出现等于null 的情况 2020-12-06 会出现session不为空但是user 为空
        //00:14: 录屏
        if(user == null) {
            throw new AppException("ART003","会话异常，请重新登录");
        }

        //用户已经登录，并且保存了用户信息
        List<Article> articles = ArticleListDAO.queryByUserId(user.getId());



        //为啥返回的之这个？？
        return articles;
    }
}
// 前端页面我没有写删除功能的时候，弹出的delete 会弹出来的框框是怎么写出来的
// ？我自己没写这里
