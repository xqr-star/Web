package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.exception.AppException;
import org.example.model.Article;
import org.example.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/articleList")
//用户会话管理，没有登录时不允许访问的
public class ArticleListServlet  extends AbstractBaseServlet{

    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession(false);//如果没有返回null  --但是为什么不是这里就已经判断了有没有对应的user吗？?


        if(session == null) {
            throw new AppException("ART002","用户没有登录，不允许访问");
        }

        //获取登录时创建session保存的用户信息
        User user = (User) session.getAttribute("user");

        //session 不为空，然后你重启tomcat 试一试，就是映射关系不存在了
        //有session，但是会存在没有对应的user -- 为什么这里还是会再次判断呢？？
        // 他说是和tomcat的实现有关系的 -- 就是如果上面判断的时false 还是回去创建这个session只是里面没有具体对应的值
        //当session不为空的时候，有可能user可能出现空的情况
        if(user == null) { // 文章列表获取失败--就是因为我重启了tomcat之前的额sesssion 无法正确映射到我的user对象
            throw new AppException("AT003","会话异常，请重新登录");
        }

        //session 是保存在tomcat服务器里面的
        //如果重启就会消失
        //顺利登录之后，拿取文章列表
        List<Article> article = ArticleDao.queryByUserId(user.getId());
        return article;

    }
}
