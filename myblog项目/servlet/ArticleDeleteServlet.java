package org.example.servlet;

import org.example.dao.ArticleListDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends AbstractBaseServlet {


    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String ids = req.getParameter("ids"); //获取什么

        //字符串切分成多个
        int num = ArticleListDAO.delete(ids.split(","));

        return  null;
    }
}
