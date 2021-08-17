package com.qiruxie.tomcat.servlets;

import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class NotFoundServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(404);
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.printf("<H1>该资源不存在 </h1>");

    }
}
