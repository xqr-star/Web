package com.qiruxie.tomcat.http;

import com.qiruxie.standard.http.Cookie;
import com.qiruxie.standard.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class HttpResponse implements HttpServletResponse {
    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public void sendError(int sc) {

    }

    @Override
    public void sendRedirect(String location) {

    }


    @Override
    public void setHeader(String name, String value) {

    }

    @Override
    public void setStatus(int sc) {

    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return null;
    }

    @Override
    public void setContentType(String type) {

    }
}
