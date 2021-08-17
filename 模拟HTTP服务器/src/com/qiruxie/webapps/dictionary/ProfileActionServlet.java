package com.qiruxie.webapps.dictionary;

import com.qiruxie.standard.http.HttpServlet;
import com.qiruxie.standard.http.HttpServletRequest;
import com.qiruxie.standard.http.HttpServletResponse;
import com.qiruxie.standard.http.HttpSession;

import java.io.IOException;

public class ProfileActionServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            resp.sendRedirect("login.html");
        }else {

            //之前不是设置过了 怎么又要设置呢
            resp.setContentType("text/plain");
            resp.getWriter().println(user.toString());
        }
    }
}
