package org.example.servlet;


import org.example.dao.ArticleDao;
import org.example.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDetail")
/**
 * 要想修改博客，必须先把我的原来文章的具体内容展示出来，再做修改。
 */
//展示文章详情的页面
public class ArticleDetailServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //解析请求数据 它的数据格式是string id
        String id = req.getParameter("id");

        //根据文章的id查询出来对应的文章
        Article a = ArticleDao.query(Integer.parseInt(id));
        //要返回一个文章对象
        //我要做修改 你需要把之前的文章内容返回给看
        return a;//返回的是一个article 对象
    }
}
