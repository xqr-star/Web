package com.qiruxie.webapps.dictionary;

import com.qiruxie.standard.ServletException;
import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class TranslateServlet extends HttpServlet {

    public TranslateServlet() {
        System.out.println("我是TranslateServlet构造方法");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("我是TranslateServlet的init");
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        System.out.println("我是Translate 的 doGet");
        String english = req.getParameter("english");

        String chinese = translate(english);
        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();

        writer.printf("<h1> 翻译服务 </h1>\r\n" );
        writer.printf("<p> %s 的意思是 %s </p>\r\n",english,chinese);
        writer.flush();


    }

    private String translate(String english) {
        String chines = english;
        return chines;
    }
}
