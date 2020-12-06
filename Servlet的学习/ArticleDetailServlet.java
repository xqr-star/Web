package org.example.servlet;

import org.example.dao.ArticleListDAO;
import org.example.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDetail")
/**
 * 要想修改博客，必须先把我的原来文章的具体内容展示出来，再做修改。
 */
public class ArticleDetailServlet  extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //解析请求数据 它的数据格式是string id
        String id = req.getParameter("id");
        Article a = ArticleListDAO.query(Integer.parseInt(id));

        //要返回一个文章对象
        //我要做修改 你需要把之前的文章内容返回给看
        return a;
    }
}
