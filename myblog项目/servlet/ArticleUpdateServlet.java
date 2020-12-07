package org.example.servlet;

import org.example.dao.ArticleListDAO;
import org.example.model.Article;
import org.example.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@WebServlet("/articleUpdate")
public class ArticleUpdateServlet  extends AbstractBaseServlet{

    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
       //输入流获取数据
        InputStream is = req.getInputStream();
       Article a = JSONUtil.deserialize(is,Article.class);

       int num = ArticleListDAO.update(a); // 我斌没有对这个num 就像使用
       return  num;

    }
}
