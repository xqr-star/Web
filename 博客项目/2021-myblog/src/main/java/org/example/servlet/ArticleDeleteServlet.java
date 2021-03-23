package org.example.servlet;

import org.example.dao.ArticleDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//其实本质就是在tomcat的web.xml
//配置了一个  url 和 请求类的名字之间的映射关系  URLName = ServletName
//这是一个注解
@WebServlet("/articleDelete")
public class ArticleDeleteServlet extends AbstractBaseServlet {
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取具体要删除哪一个文章列表
        //是用键值对解析数据的 多个之间&间隔，但是如果是请求的数据格式json就不能用Parameter
        String string = req.getParameter("ids");
        //传过来的字符串是逗号分割的--所以进行切割的操作

        //ids=1,4 然后{1,4} 但是类型string的
        int num = ArticleDao.delete(string.split(","));
        return null;

    }
}
