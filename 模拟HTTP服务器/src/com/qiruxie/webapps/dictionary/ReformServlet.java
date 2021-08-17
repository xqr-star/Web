package com.qiruxie.webapps.dictionary;

import com.qiruxie.standard.ServletException;
import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;

import java.io.IOException;

public class ReformServlet extends HttpServlet {
    public ReformServlet() {
        System.out.println("我是ReformServlet 的构造方法");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("我是RefromServlet的init 方法");
    }

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("我是doget");
    }
}
