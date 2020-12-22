package org.example.filter;

import org.example.model.JSONResponse;
import org.example.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

//配置用户统一会话管理的过滤器，匹配所有的请求路径
//服务端资源 /login 不用校验session 其他都要校验 ，如果不通过，返回401 响应内容随便加
//前端资源： /jsp/需要校验session 不通过重定向到我们的登录页面
//          其他的前端页面 /static/  /view/   / js/ 全部不校验

@WebFilter("/*")
public class LoginFilter implements Filter {
    //重写了三个方法

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * filter 每次http 请求，匹配到过滤器路径的时候，会执行该过滤器的do filter 方法
     * 如果要往下执行 调用 filterChain .doFilert(requset,response)
     *
     */
    //注意方法的返回值
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //为什么要强制类型转换
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String servletPath = req.getServletPath(); // 获取当前请求的服务路径   包括静态资源不光是servlet
        //不需要登录就允许访问的先处理
        //   /js/ 表示是一个文件夹，并且以及结束了
        if(servletPath .startsWith("/js/") || servletPath .startsWith("/static/") ||
                 servletPath .startsWith("/view/") || servletPath.equals("/login") ) {
            chain.doFilter(request,response); // 参数是没有强制转换过的。
        }else{
            //获取session 对象
            HttpSession httpSession = req.getSession(false);
            //验证用户没是否登录 如果没有登录，还需要根据前端或者后端做不同的处理
            if(httpSession == null  || httpSession.getAttribute("user") == null) {
               //前端重定向到登录页面
                if(servletPath.startsWith("/jsp/")) {
                    //需要对路径进行注意 怎么写绝对路径
                    resp.sendRedirect(basePath(req) + "/view/login.html");
                   // basePath(req)+ "/view/login.html"));

                }else{
                    //后端返回401 状态码

                    resp.setStatus(401);
                    //设置的是设置我后端的数据格式和解析的格式
                    // 前端就会以我后端设置好的格式 解析我 后端给它的格式？？
                    resp.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    JSONResponse jsonResponse = new JSONResponse();
                    jsonResponse.setCode("LG000");
                    jsonResponse.setMessage("用户没有登录，不允许访问");

                    //序列化 之后打印
                    PrintWriter pw = resp.getWriter();
                    pw.println(JSONUtil.serialize(jsonResponse));

                    pw.flush();
                    pw.close();
                }
            }else{
                //表示已经登录l 敏感资源但已经登录了是允许继续执行的
                chain.doFilter(request,response); // 参数是没有强制转换过的。
            }

        }
    }

    //根据http 请求动态的获取访问主机的访问路径
    //是服务路径之前的部分
    public static String  basePath(HttpServletRequest req){
        String schema = req.getScheme();//http
        String host = req.getServerName();//主机ip 或域名
        int port = req.getServerPort();//服务器端口号
        String contextPath = req.getContextPath();//获取应用上下文路径
        //拼接路径
        return schema + "://"+host +":" +port + contextPath;
    }

    public void destroy() {

    }
}
