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

//配置用户统一会话管理的过滤器
//会匹配所有的请求路径
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * //每次http请求，匹配到过滤器路径的时候
     * 就去执行doFilter
     * 如果往下执行，调用的方法是filterChain.doFilter(request,response)
     * 服务端 /login 不用校验 其他都要检验 如果没有登录返回401
     * 前端 只有/jsp的文件需要校验session是否登录，不通过的重定向到登录的页面
     *      /js/
     *      /view/
     *      /static/ 不用校验，直接往后走
     *      主要是因为使用的Servlet的版本是 3.1版本
     *      所以这里的doFilter不是HttpServlet 需要手动转换类型
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //大部分使用的都是自己强制类型转换的这个类
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //通过request的api 获取服务路径
        String servletPath = req.getServletPath();

        //只有需要继续往下传递 dilterChain.doFilter 才使用 Servlet Request 的api
        if(servletPath .startsWith("/js/") || servletPath .startsWith("/static/") ||
                servletPath .startsWith("/view/") || servletPath.equals("/login") ) {
            chain.doFilter(request,response); // 参数需要调用是没有强制转换过的。
        }else{
            //获取session 对象
            HttpSession httpSession = req.getSession(false);
            //验证用户没是否登录 如果没有登录，还需要根据前端或者后端做不同的处理
            if(httpSession == null  || httpSession.getAttribute("user") == null) {
                //前端重定向到登录页面
                if(servletPath.startsWith("/jsp/")) { // jsp 资源文件夹下的重定向了
                    //需要对路径进行注意 怎么写绝对路径 /
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
                    //java对象序列化为json字符串
                    pw.println(JSONUtil.serialize(jsonResponse));

                    pw.flush();
                    pw.close();
                }
            }else{
                //表示已经登录l 敏感资源但已经登录了是允许继续执行的
                chain.doFilter(request,response); // 参数是没有强制转换过的。// 过滤器本质是一个线性结构 // chain 链表过滤
            }

        }
    }

    //根据http 请求动态的获取访问主机的访问路径 访问是服务路径之前的部分
    public static String  basePath(HttpServletRequest req){
        String schema = req.getScheme();//http
        String host = req.getServerName();//主机ip 或域名
        int port = req.getServerPort();//服务器端口号
        String contextPath = req.getContextPath();//获取应用上下文路径
        //拼接路径
        return schema + "://"+host +":" +port + contextPath;
    }

    @Override
    public void destroy() {

    }
}
