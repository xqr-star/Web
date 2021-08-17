package com.qiruxie.standard;

import java.io.IOException;

//这里时一切Servlet的 核心
public interface Servlet {
    void init() throws ServletException;

    void service(ServletRequest req,ServletResponse resp) throws ServletException , IOException;

    void destroy();
}
