package org.example.servlet;

import org.example.dao.ArticleDao;
import org.example.model.Article;
import org.example.util.JSONUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleUpdate")
public class ArticleUpdateServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ServletInputStream is = req.getInputStream();
        //
        Article a = JSONUtil.deserialize(is,Article.class);

        int num = ArticleDao.update(a); // 我没有对这个num 就像使用
        return  num;
    }

}
